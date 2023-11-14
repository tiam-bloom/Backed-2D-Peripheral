# 二次元购物商城

- DDL 转 JavaBean: https://java.bejson.com/generator/

## 提交规范

| 规范名      | 描述                                          |
|----------|---------------------------------------------|
| docs     | 仅仅修改了文档，比如 README, CHANGELOG, CONTRIBUTE 等等 |
| chore    | 改变构建流程、或者增加依赖库、工具等                          |
| feat     | 新增 feature                                  |
| fix      | 修复 bug                                      |
| merge    | 合并分之                                        |
| perf     | 优化相关，比如提升性能、体验                              |
| refactor | 代码重构，没有加新功能或者修复 bug                         |
| revert   | 回滚到上一个版本                                    |
| style    | 仅仅修改了空格、格式缩进、都好等等，不改变代码逻辑                   |
| test     | 测试用例，包括单元测试、集成测试等                           |

## something
- Redis
- SpringBoot2.7.6
- MybatisPlus
- Jwt

基本的登录注册, 
权限访问, 
以及用户角色表的CRUD, 
全局统一异常处理
Jwt权限校验

> 没有任何业务逻辑, 一个基本的springboot架构框架, 只有基本的登录注册, token等
> 权限校验没有使用springsecurity框架, 使用的拦截器和jwt, 仅供参考