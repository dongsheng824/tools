package com.guods.mrcaiaddproduct;


public class App {

//	private static final String token_test = "0aea45a6-235a-423c-9731-ea0fc17f3331";
	private static final String token_prod = "7b8b3cb5-2d2b-46fb-b306-54a6def86955";
	
	public static void main(String[] args) throws Exception {
		MrcaiService mrcaiService = new MrcaiService();
//		mrcaiService.addBrandCatalogRelation(token_test);
		
//		mrcaiService.addModelNameCatalogRelation(token_test);
		
//		mrcaiService.addModelValue(token_test);
		
		mrcaiService.createProduct(token_prod);
	}
}
