# Samples
Two sample projects are provided here.
 * EdgeConfig - create and manage config for Edge
 * APIandConfig - create and manage config alongwith API deployment

## EdgeConfig
```
/samples/EdgeConfig
```

This project demonstrates the creation and management of Apigee Edge Config and performs the following steps in sequence.
  - Creates Caches
  - Creates Target servers
  - Creates API products
  - Creates Developers
  - Creates Developer Apps

To use, edit samples/EdgeConfig/shared-pom.xml, and update org and env elements in all profiles to point to your Apigee org, env. You can add more profiles corresponding to each env in your org.

      <apigee.org>myorg</apigee.org>
      <apigee.env>test</apigee.env>

To run jump to samples project `cd /samples/EdgeConfig` and run

`mvn install -Ptest -Dusername=<your-apigee-username> -Dpassword=<your-apigee-password> -Dapigee.config.options=create -DencryptPwd=pwd`

**NOTE: All the encrypted client credentials used in the sample was encrypted using `-DencryptPwd=pwd`**


## APIandConfig

Create config and deploy API
```
/samples/APIandConfig/HelloWorld
```

This project demonstrates use of apigee-config-maven-plugin and [apigee-deploy-maven-plugin](https://github.com/apigee/apigee-deploy-maven-plugin) to create API as well as manage config. The example project performs the following steps in sequence. This sequence is inherent to the platform and is managed using the sequencing of goals in pom.xml
  - Creates Caches
  - Creates Target servers
  - Deploy API  (from deploy plugin)
  - Creates API products
  - Creates Developers
  - Creates Developer Apps

To use, edit samples/APIandConfig/shared-pom.xml, and update org and env elements in all profiles to point to your Apigee org, env. You can add more profiles corresponding to each env in your org.

      <apigee.org>myorg</apigee.org>
      <apigee.env>test</apigee.env>

To run jump to samples project `cd /samples/APIandConfig/HelloWorld` and run

`mvn install -Ptest -Dusername=<your-apigee-username> -Dpassword=<your-apigee-password> -Dapigee.config.options=create -DencryptPwd=pwd`

**NOTE: All the encrypted client credentials used in the sample was encrypted using `-DencryptPwd=pwd`**


## Encrypt consumerKey and consumerSecret in configurations

Consumer Key and secret can be passed as part of the configuration which can be set as the key and secret while configuring the Developer App in Edge. This is mainly useful for CI/CD where the configurations can be used for functional testing as part of the pipeline. Since this configuration is part of the SCM system, we have included encryption to the data using [PBKDF2](https://en.wikipedia.org/wiki/PBKDF2) which requires a password with which the encryption happens and the same needs to be provided to decrypt.

For the same, we have included an encrypt task within the plugin as well where you can pass the actual client id and secret that needs to be decrypted. The command for the same is
`mvn apigee-config:encrypt -DencryptData=<text-to-be-encrypted> -DencryptPwd=<your-encryption-password>`

Once the command is run, you can find the encrypted data in the console

``` ************************************************************************

[INFO] Encrypted Data: OsrBNRmsg/XNVbpS0vIr7to93T9qUcbjXzPaLGsO5btcG/w//St0UVzbBJLZFl2i

[INFO] You can provide this info in the edge.json for either consumerKey or consumerSecret.
Make sure you have the password handy
------------------------------------------------------------------------
```

This encrypted info should be passed in edge.json or developerApps.json in case you want to set up specific Client ID and secret for the developers apps. Below is an example

```
"hugh@example.com": [
        {
            "apiProducts": [
                "weatherProduct"
            ],
            "callbackUrl": "http://weatherapp.com",
            "name": "hughapp",
            "scopes": [],
            "consumerKey":"OsrBNRmsg/XNVbpS0vIr7to93T9qUcbjXzPaLGsO5btcG/w//St0UVzbBJLZFl2i",
            "consumerSecret":"tPmKusaLJYtI8ENmb/+SXhkXfOJkE2vjtPyvl3wX57PdFdOL53FJ/VUAJBb4vAn6"
        }
    ]
```

In the above example, the encrypted values were generated using `pwd` as the password. The same can be used for decrypting as well using the decrypt task (same as encrypt)

`mvn apigee-config:decrypt -DencryptPwd=pwd -DencryptData=OsrBNRmsg/XNVbpS0vIr7to93T9qUcbjXzPaLGsO5btcG/w//St0UVzbBJLZFl2i`

During runtime, the plugin will take the encryptPwd to decrypt the configurations and use that while invoking Management API.

Please refer to our samples.
