package com.careerit.scart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.careerit.scart.domain.Product;
import com.careerit.scart.mapper.ProductMapper;

@Repository
public class ProductDaoImpl implements ProductDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);

	private static final String SELECT_PRODUCTS = "select pid,name,description,price from product";
	private static final String ADD_PRODUCT = "insert into product(name,description,price) values(?,?,?)";
	private static final String DELETE_PRODUCTS = "truncate table product";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long insertProduct(Product product) {

		KeyHolder keyholder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(ADD_PRODUCT,Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, product.getName());
				pst.setString(2, product.getDescription());
				pst.setDouble(3, product.getPrice());
				return pst;
			}
		}, keyholder);
		long id = keyholder.getKey().longValue();
		LOGGER.info("Product is added with id :{}", id);
		return id;
	}

	@Override
	public List<Product> selectAllProducts() {
		// new BeanPropertyRowMapper<Product>(Product.class)
		List<Product> productList = jdbcTemplate.query(SELECT_PRODUCTS, new ProductMapper());
		LOGGER.info("Total product found in DB: {}", productList.size());
		return productList;
	}

	@Override
	public Product selectProductById(long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> search(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long updateProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteProduct(long pid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long insertProducts(List<Product> products) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long updateProducts(List<Product> products) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteProduct() {
		
			jdbcTemplate.update(DELETE_PRODUCTS);

	}

}
