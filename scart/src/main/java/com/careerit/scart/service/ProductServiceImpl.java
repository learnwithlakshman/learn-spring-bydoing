package com.careerit.scart.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.careerit.scart.dao.ProductDao;
import com.careerit.scart.domain.Product;
import com.careerit.scart.service.excpetion.ProductAlreadyExistsException;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Override
	public Product addProduct(Product product) {
		Assert.notNull(product, "Product can't be null");
		Assert.notNull(product.getName(), "Product name can't be null or empty");
		
		Product p= productDao.selectProductByNameAndPrice(product.getName(),product.getPrice());
		if(p != null) {
			throw new ProductAlreadyExistsException(String.format("Product with name %s and price %s already exists",product.getName(),product.getPrice()));
		}
		long id = productDao.insertProduct(product);
		product.setPid(id);
		LOGGER.info("Product is added with id :{}", id);
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = productDao.selectAllProducts();
		LOGGER.info("Total products found :{}", productList.size());
		return productList;
	}

	@Override
	public Product updateProduct(Product product) {
		Assert.notNull(product, "Product can't be null");
		Assert.notNull(product.getName(), "Product name can't be null or empty");
		Assert.notNull(product.getPid(), "Product id can't be null or empty");
		long id = productDao.updateProduct(product);
		if (id == 0) {
			LOGGER.info("Product with id {} was not able update reason could be product is not found ",
					product.getPid());
		}
		return product;
	}

	@Override
	public Product getProductById(long pid) {
		   return productDao.selectProductById(pid);
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
	public long addProducts(List<Product> products) {
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
		// TODO Auto-generated method stub

	}

}
