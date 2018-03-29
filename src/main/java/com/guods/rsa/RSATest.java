package com.guods.rsa;

public class RSATest {

	public static void main(String[] args) throws Exception {
		String data = "这是数据";

		String existPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPuBO4Mh/3XnJrc0RBegCeAEiAuFBJlL534BsHWFS8LoZuziPczuSIvaI6floXr5ET71F8mxb/sefeCnlshLk/FuH5u6pZHvdnDehuHacfydjabH5K4whpQx12ygOaPQXEC4zWMwKE51ShuVDCNRPiYEOGhD6OESB9Jiv/0UqfWwIDAQAB";
		String existPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+4E7gyH/decmtzREF6AJ4ASIC4UEmUvnfgGwdYVLwuhm7OI9zO5Ii9ojp+WhevkRPvUXybFv+x594KeWyEuT8W4fm7qlke92cN6G4dpx/J2NpsfkrjCGlDHXbKA5o9BcQLjNYzAoTnVKG5UMI1E+JgQ4aEPo4RIH0mK//RSp9bAgMBAAECgYANvAKubKPfaPg4SoiwVS9v6gkDvBl4Hq3hGNqcZhWa8jtIpYyJ0n/GjZDOVDrZ3p5XsFwyM0QMZX0KHCgkdb6aIumqXpmCdj9f8P7d9CNCPuBKWw4B7GaasquvwYzRpXKjslVQ8Eqgg/QUwkDmr2rbwnVmxZUqZdwfA5aF6q6JaQJBANnE2r3Q8H1/P52u9bPthQFsC6XhhmUoayV5UkGhtJoVn6t77i5MtzLxIWDRRhI4qZUWiEUghS8gjMzRYrMkr08CQQCo8zZxaGMKcWbm7uqeE8JYKRGCtEWEbyx07zZtItwaCTvBwbhqQulvXgkuc5fBLwyYR6JcOQ3JaaWPSXdsM2w1AkBS4oofFRdzrKsZ6S27ffvRvr3wNRZiCjA94x9bu6BxxSkwq8rL9IcF7KE3qG9zH/3rsNfM98O5XDo6+rcGWEAhAkBVjSLKdpOAXEBBPEYt2c+VmOY3C1YQY8NdSyyCQx61SGusB458sHlIXZPEo9/6gPqdudhrXrCuhwLA9fC1J6zpAkAU9xQNYACas9vvvZxH3oqKsALHqQBEnB4r7OnEN08ueovUlPsr3DXsVe+xuthO8bg9+KmgbeWeEO0EArLsPl5a";

		// // 生成密钥
		// KeyPair keyPair = RSAUtil.genKeyPair(1024);
		// // 公钥
		// PublicKey publicKey = keyPair.getPublic();
		// byte[] encode = Base64.getEncoder().encode(publicKey.getEncoded());
		// System.out.println("公钥：" + new String(encode));
		// // 私钥
		// PrivateKey privateKey = keyPair.getPrivate();
		// encode = Base64.getEncoder().encode(privateKey.getEncoded());
		// System.out.println("私钥：" + new String(encode));
		//
		// // 公钥加密
		// byte[] encrypt = RSAUtil.encrypt(data.getBytes(), publicKey);
		// //加密后的数据base64编码成secData传输
		// String secData = Base64.getEncoder().encodeToString(encrypt);
		// System.out.println("公钥加密后：" + secData);
		//
		// //接收secData，base64解码为byte数组
		// byte[] secDataByte = Base64.getDecoder().decode(secData);
		// // 私钥解密
		// byte[] decrypt = RSAUtil.decrypt(secDataByte, privateKey);
		// System.out.println("私钥解密后：" + new String(decrypt));

		////////////////////////// 用已生成好的公钥和私钥加密解密
		// 公钥加密
		String encodedData = RSAUtil.encrypt2Str(data, RSAUtil.getPublicKey(existPublicKey));
		System.out.println("已生成的公钥加密后：" + new String(encodedData));
		// 私钥解密
		String decodeData = RSAUtil.decrypt2Str(encodedData, RSAUtil.getPrivateKey(existPrivateKey));
		System.out.println("已生成的私钥解密后：" + new String(decodeData));

		// 私钥加密
		encodedData = RSAUtil.encrypt2Str(data, RSAUtil.getPrivateKey(existPrivateKey));
		System.out.println("已生成的私钥加密后：" + new String(encodedData));
		// 公钥解密
		decodeData = RSAUtil.decrypt2Str(encodedData, RSAUtil.getPublicKey(existPublicKey));
		System.out.println("已生成的公钥解密后：" + new String(decodeData));
	}
}
