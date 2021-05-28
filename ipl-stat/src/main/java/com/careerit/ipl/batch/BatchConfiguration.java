package com.careerit.ipl.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.repo.PlayerRepo;
import com.careerit.ipl.repo.TeamDetailsRepo;

@Component
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private StepBuilderFactory sf;
	@Autowired
	private JobBuilderFactory jf;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PlayerRepo playerRepo;

	@Autowired
	private TeamDetailsRepo teamDetailsRepo;

	@Bean
	public FlatFileItemReader<PlayerItem> reader() {

		return new FlatFileItemReaderBuilder<PlayerItem>().name("playerItemReader")
				.resource(new ClassPathResource("ipl_2021.csv")).delimited()

				.names(new String[] { "name", "role", "country", "team", "price" }).linesToSkip(1)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PlayerItem>() {
					{
						setTargetType(PlayerItem.class);
					}
				}).build();
	}

	@Bean
	public MongoItemWriter<Player> writer() {
		return new MongoItemWriterBuilder<Player>().template(mongoTemplate).build();
	}

	@Bean
	public ItemProcessor<PlayerItem, Player> processor() {
		return new PlayerItemProcessor();
	}

	@Bean
	public Step step1() {
		return sf.get("step1").<PlayerItem, Player>chunk(20).reader(reader()).processor(processor()).writer(writer())
				.build();
	}

	@Bean
	public Job importPlayers(Step step1) {
		return jf.get("importPlayer").incrementer(new RunIdIncrementer()).listener(listener()).flow(step1).end()
				.build();

	}

	@Bean
	public JobExecutionListener listener() {
		return new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {
				System.out.println(this.getClass());
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
					System.out.println("Completed");

					List<Player> playersList = playerRepo.findAll();

					Map<String, List<Player>> map = playersList.stream()
							.collect(Collectors.groupingBy(Player::getTeam));

					List<TeamDetails> teamList = new ArrayList<>();

					map.entrySet().forEach(ele -> {
						String name = ele.getKey();
						List<Player> players = ele.getValue();
						int count = players.size();
						double amount = players.stream().mapToDouble(Player::getPrice).sum();
						TeamDetails obj = new TeamDetails();
						obj.setName(name);
						obj.setCount(count);
						obj.setAmount(amount);
						teamList.add(obj);
					});

					teamDetailsRepo.saveAll(teamList);
				}

			}
		};
	}

}
