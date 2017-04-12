/**
 * Copyright (C) 2016 Apigee Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apigee.edge.config.mavenplugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apigee.edge.config.utils.Crypto;

/**                                                                                                                                     ¡¡
 * Goal to create encrypted values for consumer credentials
 * scope: org
 *
 * @author saisaran.vaidyanathan
 * @goal encrypt
 * @phase verify
 */

public class EncryptMojo extends GatewayAbstractMojo
{
	static Logger logger = LoggerFactory.getLogger(EncryptMojo.class);
	public static final String ____ATTENTION_MARKER____ =
	"************************************************************************";

	private String data = null;
	private String password = null;
	
	public EncryptMojo() {
		super();

	}
	
	public void init() throws MojoFailureException {
		try {
			logger.info(____ATTENTION_MARKER____);
			logger.info("Encrypt data");
			logger.info(____ATTENTION_MARKER____);

			String encryptData="";
			String encryptPwd= "";
			encryptData = super.getEncryptData();
			if (encryptData != null) {
				data = encryptData;
			}
			encryptPwd = super.getEncryptPwd();
			if (encryptPwd != null) {
				password = encryptPwd;
			}
			if(encryptData == null || encryptPwd == null)
				throw new MojoFailureException("Provide both encryptData and encryptPwd as arguments for this option");
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid option provided");
		} catch (RuntimeException e) {
			throw e;
		}

	}
	
	
	/** 
	 * Entry point for the mojo.
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		Logger logger = LoggerFactory.getLogger(EncryptMojo.class);
		if (super.isSkip()) {
			getLog().info("Skipping");
			return;
		}
		try {
			init();
			logger.info("Encrypted Data: "+Crypto.encrpytString(password, data));
			logger.info("You can provide this info in the edge.json for either consumerKey or consumerSecret. \n Make sure you have the password handy");
		} catch (MojoFailureException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}




