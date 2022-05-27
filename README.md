# Report

> Due to the large amount of code, the important APIs are introduced.

## Group Information

| Name               | 林洁芳                                                       | 汤奕飞                                                       |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| SID                | 12011543                                                     | 12011906                                                     |
| Lab session        | Lab 2                                                        | Lab 2                                                        |
| Tasks              | server (include using springboot, mybatisplus and database connection pool and designing all back-end APIs, etc) | client (include displaying UI, sending request and processing response, etc) |
| Contribution ratio | 50%                                                          | 50%                                                          |

## API Specification & Advanced Requirements

### Server Part

#### Controller Layer

The return value type of all parameters is **Data class**, which is the agreed response data format with the front-end.

```java
package com.database.projectii.controller.transmission;

public class Data {
    // object data information
    private Object data;
    // whether the transmission is successful
    private String message;
}
```

Controller layer is responsible for transferring data between the page and the application.

**InventoryController.stockIn()**

Corresponds to the basic part of the **insert inventory**, using http POST requests to send data in Json format, converted into objects and passed into the server layer.

```java
@PostMapping
public Data stockIn(@RequestBody Inventory inventory);
```

**OrderController.updateOrder()**

Corresponds to the basic part of the **update order**, using http PUT requests to send data in Json format, converted into the object array and passed into the server layer.

```java
@PutMapping
public Data updateOrder(@RequestBody Order[] orders);
```

**OrderController.deleteOrder()**

Corresponds to the basic part of the **delete order**, using http DELETE request parameters and passed into the server layer.

```java
@DeleteMapping
public Data deleteOrder(@RequestParam("contract") String contract,
                        @RequestParam("salesman") String salesman,
                        @RequestParam("seq") Integer seq);
```

**OrderController.placeOrder()**

Corresponds to the basic part of the **insert order**, using http POST requests to send data in Json format, converted into the object array and passed into the server layer.

```java
@PostMapping
public Data placeOrder(@RequestBody Order[] orders);
```

**StaffController.getAllStaffCount()**

> Return the numbers of people for all types of staffs.

```java
@GetMapping("/getAllStaffCount")
public Data getAllStaffCount();
```

**ContractController.getContractCount()**

> Return the total number of existing contracts.

Execute this method to get the number of contracts based on the http GET request path.

```java
@GetMapping("/getContractCount")
public Data getContractCount();
```

**OrderController.getOrderCount()**

> Return the total number of existing orders.

Accept the request and calculate the order quantity.

```java
@GetMapping("/getOrderCount")
public Data getOrderCount();
```

**InventoryController.getNeverSoldProductCount()**

> The number of product_models that are in stock but have never been ordered (sold).

```java
@GetMapping("/getNeverSoldProductCount")
public Data getNeverSoldProductCount();
```

**OrderController.getFavoriteProductModel()**

> Find the models with the highest sold quantity, and the number of sales.

```java
@GetMapping("/getFavoriteProductModel")
public Data getFavoriteProductModel();
```

**OrderController.getAvgStockByCenter()**

> For each supply center, calculate the average quantity of the remaining product models. The results should be ordered by the name of the supply centers and rounded to one decimal place.

```java
@GetMapping("/getAvgStockByCenter")
public Data getAvgStockByCenter();
```

**InventoryController.getProductByNumber()**

> Find a product according to the product number and return the current inventory capacity of each product model in each supply center.

```java
@GetMapping("/getProductByNumber/{productNumber}")
public Data getProductByNumber(@PathVariable String productNumber);
```

**OrderController.getContractInfo()**

> Find a contract with a contract number and return the content of the contract.

```java
@GetMapping("/getContractInfo/{contractNumber}")
public Data getContractInfo(@PathVariable String contractNumber);
```

#### Mapper Layer

Because of the **mybatisplus traversal**, some of the simple SQL statements can be customized without the need to customize some of the more complex SQL statements, such as the use of aggregate functions, multi-table nesting and continuous table queries. 

As OrderMapper class (part) for example:

```java
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from (select product_model, sum(quantity) as sum from orders " +
        "group by product_model)sub where sub.sum = (select sum(quantity) as max_sum from orders " +
        "group by product_model order by max_sum desc limit 1)")
    List<Map<String, Object>> selectFavoriteProductModel();
}
```

#### Model Layer

The entity class package, which contains all the implementation classes of the corresponding entity classes.

#### Service Layer

Using the data passed from the controller layer, the logic of inserting, deleting, updating and selecting is implemented, and the result is returned to the controller layer.

**StaffServiceImpl.selectAllTypeStaffCount()**

```java
//return List<Map<String, Object>> type data, pass back to controller layer.
public List<Map<String, Object>> selectAllTypeStaffCount() {
    QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
    staffQueryWrapper.select("type, count(*) as cnt");
    staffQueryWrapper.groupBy("type");
    return staffMapper.selectMaps(staffQueryWrapper);
}
```

**ContractServiceImpl.selectContractCount()**

```java
// use QueryWrapper to add some constraints.
// return List<Map<String, Object>> type data, pass back to controller layer.
public Object selectContractCount(){
    QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("count(distinct contract_number)");
    return orderMapper.selectObjs(queryWrapper);
}
```

**InventoryServiceImpl.java**

**Reasonableness check for incoming parameters instead of database dependent trigger check**. Generally, the validity of the data is ensured before it enters the database to avoid unnecessary burden on the database.

```java
// parasmeter: inventory, to check whether it is reasonableness.
// return: bool type to help client judge whether this operator is successful. 
public boolean insert(Inventory inventory);

// Call a custom SQL statement that returns the List<Map<String, Object>> type.
public long selectNeverSoldProductCount() {
    return inventoryMapper.selectNeverSoldProductCount();
}

public List<Map<String, Object>> selectProductByNumber(String productNumber) {
    return inventoryMapper.selectProductByNumber(productNumber);
}
```

**OrderServiceImpl.java**

```java
package com.database.projectii.service.impl;

@Service
public class OrderServiceImpl implements OrderService {
    // Check the insertion, check the data coming from the controller layer, and write the data to the database using the insertion method that comes with it.
    public boolean insert(Order order);
    
    // Update order: Find unique matching orders with some parameters and update them.
    public boolean updateQuantityEstLod(Order order);

    // Override compareTo methods in Order.java in model layer.
    public boolean deleteByContractSalesmanSeq(String contract, String salesman, Integer seq);

    public Object selectOrderCount() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return orderMapper.selectObjs(queryWrapper);
    }

    // Call a custom SQL statement to perform a query and return the result.
    public List<Map<String, Object>> selectFavoriteProductModel() {
        return orderMapper.selectFavoriteProductModel();
    }

    public List<Map<String, Object>> selectAvgStockByCenter() {
        return orderMapper.selectAvgStockByCenter();
    }

    // The query results wrapped into an object, using the convenience of springboot framework, you can convert to Json format to transfer back to the client.
    public List<ReturnOrder> selectContractInfo(String contractNumber);
}
```

#### Conclusion (both basic and advanced part)

**1>** Complete all the functions of the basic part, including data addition, deletion, all specific APIs and one-click import and export functions. **(In output.pdf in sakai)**

**2>** Realize front-end and back-end design pattern in this project. The back-end is a tomcat server built using the **springboot framework**. Use **json format** for asynchronous data interaction. The database engine used is postgresql, and the use of **mybatisplus** persistence layer **ORM framework** to ensure scalability. The overall interface design follows the **restful style**.

![server structure](graphs\server_structure.png)

**3>** Use Alibaba's druid as database connection pool.

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.9</version>
</dependency>
```

**4>**  **Use ContiPerf to realize pressure test**. 

This stress test focuses on **volume** testing. You can set different parameters or change methods to test the API of the service layer. Here is a sample of one of the tests.

```java
@PerfTest(invocations = 10000, threads = 1000000)
@Required(max = 1000000, average = 200000, totalTime = 60000000)
public void test() {
    Random random = new Random();
    Staff staff = new Staff(random.nextInt(999) + 1, null, null, null, null, null, null, null);
    System.out.println(staffServiceimpl.selectStaffByAny(staff));
}
```

The following shows the report of the stress test.

![pressure test](graphs\pressure_test.png)

### Client Part 