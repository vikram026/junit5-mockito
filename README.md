***Junit5-mockito(ecom-store)***


**Description**

    `This Project is consisting of ecom-like application where different users can order thier items and if item is not not available he gets out of stock messages (based on the conditions applied)`
    `Here I had tried to make the things very simple and focused on the logic implementation`

**Technologies used**

    *Java 1.8, Mysql,SpringBoot,Juint5, JPA,Hibernate Mapping,RestFul Webservices, Gradle,Postman,*
    
    
**How to Run the Appliation** 

    clone the project using https://github.com/vikram026/ecom-store.git
    build the project and make sure all the component test cases are passed 
    Once the build is success run the springboot application;
    

**Prerequisites**
    
    1.Mysql should be installed in local with user name root and no password;
    create database "ecomdb" tables will be created by hibernate
    
**Actual Process**



    **ItemPreparation**
    
    2.prepare the data by hitting the API using Postman 
        a.Create 10 items in item table using postman using below url and body in json;
            POST: http://localhost:8991/item        (for save/update)
            with body like below:
            
            
            {
            	"id":1,
            	"name":"pencil",
            	"qty":100,
            	"price":20
            }
            
            like that prepare more or less 10 items so that one can order more than one item;
    3.once the data is prepared 
        test these data using  GET:- http://localhost:8991/item
            it will list all the item with its id;
            using these itemId we are going to order items;
    4. one can delete the item using DELETE:-http://localhost:8991/item?id=2    here id is itemid
    
    
    
    
    
    Placing the Order 
    
    single/bulk order by customer
    5.  POST:-localhost:8991/order
    
    A customer can order single or multiple item or more then one quantity of single item changing the payload
    
    
      
      
      
     payload for single item be like :-
     
     {
     	"orderId":1,
     	"orderedItems":[ 
     		{
     		"id":5,
     		"itemId":1,
     		"qty":1
     		}
     	],
     	"emailId":"vikram19977@gmail.com"
     }
     
     
     payload for more than one quantity of single item
          {
          	"orderId":1,
          	"orderedItems":[ 
          		{
          		"id":5,
          		"itemId":1,
          		"qty":4
          		}
          	],
          	"emailId":"vikram19977@gmail.com"
          }
         
         
          
      payload for more then one item 
     
       {
            "orderId":1,
            "orderedItems":[ 
                {
                "id":5,
                "itemId":1,
                "qty":1
                },
                 {
                    "id":5,
                    "itemId":1,
                    "qty":1
                 }
            ],
            "emailId":"testuser@gmail.com"
        }  
        
        
       i. If we have happy path means order is placed  then we will get the message like ::
        'sample output'-  "Order Placed...!  you have spent :"+xxxx +" Rupees";  xxxx=calculated total spent amount;
       
       
       
       ii. for any of the exception like out of stock item we have various messages coming to the customer accordinglg
      
      
      
      It is already tested by various component test cases written in test folder 
      for both ItemController and OrderController;
      
      
      
      6.GET:localhost:8991/order -> for listing all orders placed by all the customers
      
      sample output:
      
      
      [
          {
              "orderId": 66,
              "orderedItems": [
                  {
                      "id": 67,
                      "itemId": 1,
                      "qty": 30
                  },
                  {
                      "id": 68,
                      "itemId": 3,
                      "qty": 2
                  }
              ],
              "emailId": "vikram@234.com",
              "amount": 2150
          },
          {
              "orderId": 69,
              "orderedItems": [
                  {
                      "id": 70,
                      "itemId": 1,
                      "qty": 5
                  },
                  {
                      "id": 71,
                      "itemId": 2,
                      "qty": 5
                  }
              ],
              "emailId": "vikram@234.com",
              "amount": 250025
          },
          {
              "orderId": 72,
              "orderedItems": [
                  {
                      "id": 73,
                      "itemId": 1,
                      "qty": 5
                  },
                  {
                      "id": 74,
                      "itemId": 2,
                      "qty": 5
                  }
              ],
              "emailId": "vik123@gmail.com",
              "amount": 250025
          }
      ]
      
      
      
      7.GET:localhost:8991/order/vik123@gmail.com  -> for listing the orders per customer separately;  
       here email id is customer email id;
       
      sample output:
      [
          {
              "orderId": 72,
              "orderedItems": [
                  {
                      "id": 73,
                      "itemId": 1,
                      "qty": 5
                  },
                  {
                      "id": 74,
                      "itemId": 2,
                      "qty": 5
                  }
              ],
              "emailId": "vik123@gmail.com",
              "amount": 250025
          }
      ]
       
     8.In TestCases used Junit5 concepts as well with the 87% of testcase coverage;
     
     
       
      
          
        
        


    
  
