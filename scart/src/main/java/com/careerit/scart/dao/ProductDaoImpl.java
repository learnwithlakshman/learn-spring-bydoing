package com.careerit.scart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
	private static final String PRODUCT_BY_ID = "select pid,name,description,price from product where pid = ? ";
	private static final String SEARCH_PRODUCT = "select pid,name,description,price from product where name like '%'?'%'";
	private static final String UPDATE_PRODUCT = "update product set name=?,description=?,price=? where pid = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long insertProduct(Product product) {

		KeyHolder keyholder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS);
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
	public long updateProduct(Product product) {
		jdbcTemplate.update(UPDATE_PRODUCT,
				new Object[] { product.getName(), product.getDescription(), product.getPrice(), product.getPid() });
		LOGGER.info("Product with id :{} is updated with name :{} price:{}", product.getPid(), product.getName(),
				product.getPrice());
		return product.getPid();
	}

	@Override
	public Product selectProductById(long pid) {
		return jdbcTemplate.queryForObject(PRODUCT_BY_ID, new Object[] {pid},new int[] {Types.INTEGER},new BeanPropertyRowMapper<Product>(Product.class));
	}

	@Override
	public List<Product> search(String str) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Product selectProductByNameAndPrice(String name, double price) {
		List<Product> products = jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con
						.prepareStatement("select pid,name,price,description from product where name=? and price=?");
				pst.setString(1, name);
				pst.setDouble(2, price);
				return pst;
			}
		}, new BeanPropertyRowMapper<Product>(Product.class));
		return products.size() == 0 ? null : products.get(0);
	}

}
