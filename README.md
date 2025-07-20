# springboot-tools


## 验证工具类

* IsNumeric 判断是否是整数，可以设置最大值和最小值
* IsBoolean 判断是否是布尔型，支持 true、false、1、0
* IsList 判断是否在数组中，支持 String、Integer
* IsNum 判断是否在枚举中，支持 String、Integer
* IsMobile 判断中国手机号
* IsBankCard 判断银行卡号，使用 Luhm算法验证
* IsCreditCode 判断统一社会信用代码，使用加权银子校验最后一位
* IsIdCard 判断身份证号
* IsUnique 判断表字段是否唯一，支持更新主键过滤，支持多字段组合验证

## 系统工具类

使用HuTool工具包，涵盖了常用的操作，简单封装就可以，无须再重复编码，详细见文档 https://doc.hutool.cn。

## API 日志记录，方便接口对接排查

* 服务接口调用使用@ApiLogIn注解,自动记录内部调用接口响应结果。
* 外部服务调用使用@ApiLogOut注解HttpClient客户端，自动记录外部调用接口响应结果。

## 重复请求注解，防止接口重复提交


