package com.guods.mrcaiaddproduct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.guods.tools.HttpClientUtil;

public class MrcaiService {

	private static final String addBrandCatalogRelationUrl = "http://test.admin.mrcai.com/mrcai/web/maintenance/addBrandCatalogRelation.do";
	private static final String addModelNameCatalogRelationUrl = "http://test.admin.mrcai.com/mrcai/web/maintenance/addModelNameCatalogRelation.do";
	private static final String addModelValueUrl = "http://test.admin.mrcai.com/mrcai/web/maintenance/addModelValue.do";
	private static final String createProductUrl = "http://test.admin.mrcai.com/mrcai/web/maintenance/createProduct.do";
	
	private static final String addBrandCatalogRelationFileName = "D:\\项目文档\\product import\\addBrandCatalogRelation.txt";
	private static final String addModelNameCatalogRelationFileName = "D:\\项目文档\\product import\\addModelNameCatalogRelation.txt";
	private static final String addModelValueFileName = "D:\\项目文档\\product import\\addModelValue.txt";
	private static final String createProductFileName = "D:\\项目文档\\product import\\createProduct.txt";
	
	/**
	 * 添加品牌和三级类目关联
	 * @throws IOException
	 */
	public void addBrandCatalogRelation(String token) throws Exception {
		execute(token, addBrandCatalogRelationFileName, addBrandCatalogRelationUrl);
	}
	
	/**
	 * 添加规格属性和三级类目关联
	 * @param token
	 * @throws Exception
	 */
	public void addModelNameCatalogRelation(String token) throws Exception {
		execute(token, addModelNameCatalogRelationFileName, addModelNameCatalogRelationUrl);
	}
	/**
	 * 增加属性值
	 * @param token
	 * @throws Exception
	 */
	public void addModelValue(String token) throws Exception {
		execute(token, addModelValueFileName, addModelValueUrl);
	}
	/**
	 * 创建产品
	 * @param token
	 * @throws Exception
	 */
	public void createProduct(String token) throws Exception {
		execute(token, createProductFileName, createProductUrl);
	}
	
	public void execute(String token, String fileName, String url) throws Exception {
		String json = null;
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			FileInputStream fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((json = bufferedReader.readLine()) != null) {
				System.out.println(json);
				//发送请求
				if (json.trim().length() > 0) {
					HttpClientUtil.post(url, json, token);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}
}
