# springboot集成keycloak说明
【服务简介】
该应用使用springboot + templates 集成keycloak，搭建了一个简单的demo,主要目的是为了了解keycloak对后端应用的保护。

### 1.安装检查项
````
    1. keycloak正常部署启动
    2. keycloak各项配置与应用的kecloak配置一致.
        主要牵扯keycloak.realm，keycloak.resource， keycloak.credentials.secret
    3. keycloak用户与用户权限设置。
````
### 2.keycloak配置
````
应用配置
````
![](doc/服务配置1.jpg)
![](doc/服务配置2.jpg)
````
权限配置
````
![](doc/权限配置.jpg)
````
用户及用户权限配置，
    示例中创建了admin和customer用户，为admin添加ROLE_ADMIN权限，为customer添加ROLE_CUSTOMER权限
````
![](doc/用户配置.jpg)
![](doc/admin.jpg)
![](doc/customer.jpg)
### 3.代码结构介绍
````
    无
````
### 4.服务启动
```
    springboot本地启动：
        com.sinosun.security.keycloack.KeycloackApplication.main
    服务启动后访问：http://127.0.0.1:8083
```
