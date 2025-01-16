# Spring framework V.S. Spring boot 

Spring framework provides comprehensive infrastructure support for developing Java applications. 

Spring Boot is basically an extension of the Spring framework, which eliminates the boilerplate configurations required for setting up a Spring application.

# Spring framework concept

**Dependency Injection (DI)** is a design pattern where an object (or class) receives its dependencies from an external source rather than creating them itself. 
In Spring, the **IoC (Inversion of Control)** Container is responsible for managing the objects' lifecycle and injecting dependencies when needed.

A **bean** is simply an object that is created, 
assembled, and managed by Spring's IoC container. The Spring IoC container is responsible for:

- Creating bean instances.
- Managing dependencies between beans (Dependency Injection).
- Managing the lifecycle of beans (initialization and destruction).

# Ways to make object spring managed

1) XML-based configuration

2) Java-based configuration

3) Annotation-based configuration


In Spring Boot, annotations are heavily used to manage objects, their lifecycle, and dependencies.

# Springboot layer

**Controller**

    Purpose: Handles HTTP requests and responses.
    
    Responsibilities:
    Accept and process user input from web pages, REST APIs, etc.
    Call service methods to perform business logic.
    Return responses, often in JSON or HTML format.

**Service**

    Purpose: Contains business logic and application rules.

    Responsibilities:
    Orchestrate operations by coordinating between different layers.
    Apply validations, calculations, and processing logic.


**Repository**

    Purpose: Handles interaction with the database.
    
    Responsibilities:
    Perform CRUD (Create, Read, Update, Delete) operations.
    Encapsulate database queries.

**Model**

    Purpose: Represents the application's core data structures and entities.
    
    Responsibilities:
    Define the structure of the data.
    Annotate with JPA or Hibernate annotations to map objects to database tables.

# Spring boot annotations

1. Component Scanning and Bean Creation
   
- `@Component`: Marks a class as a Spring-managed component.
- `@Service`: A specialized version of `@Component`, used for service-layer logic.
- `@Repository`: A specialized version of `@Component`, used for the persistence layer.
- `@Controller`: Indicates that the class is a controller in an MVC architecture.
- `@RestController`: Combines `@Controller` and `@ResponseBody` for REST APIs.

2) Dependency Injection
   
- `@Autowired`: Automatically injects dependencies into a bean (by type).

    Is used for Dependency Injection (DI) in Spring.
  
    It can be applied to fields, constructors (recommended), or setter methods.
  
    It tells Spring to inject the appropriate bean from the IoC container into the target class.

    When to Use `@Autowired`?
  
    **In Services**: When you need to inject another service or repository into your service layer.
  
    **In Controllers**: When injecting service beans to handle business logic.
  
    **In Tests**: When you need to automatically inject beans for testing.
  
3) Configuration and Bean Definitions
   
- @Configuration: Marks a class as a source of bean definitions.
- @Bean: Indicates that a method produces a bean managed by the Spring container.
  
    Using `@Bean` in a `@Configuration` Class

    You can explicitly define beans using the `@Bean` annotation inside a configuration class.
   
    This gives you **more control over the bean creation process**

    **Configuration Class**: It is used to define beans explicitly using Java methods. The methods inside a @Configuration class annotated with @Bean will be used to create Spring beans.

    **Bean Definition**: Each method annotated with @Bean within a @Configuration class represents a bean definition. The Spring container manages these beans like it does for beans created via @Component.

4) **Automatic Bean Detection** with `@SpringBootApplication`
    
    When you annotate your main application class with `@SpringBootApplication`, it enables component scanning automatically. 
  
    This will scan the package where the main class is located and all sub-packages for components (like `@Component`, `@Service`, `@Repository`, etc.) and automatically register them as beans.

5) Web Annotations
   
- `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.: Map HTTP requests to specific controller methods.
- `@PathVariable`: Extract values from the URI.
- `@RequestParam`: Extract query parameters from the request.

# .properties file

A .properties file in Java is a simple text file used to store key-value pairs, where both the key and value are strings. These files are commonly used for:

Configuration: Storing application settings like database URLs, usernames, passwords, etc.

    Some important syntax
    
    spring.datasource.url=your database URL
    spring.datasource.username=Database Username
    spring.datasource.password=Database Pass
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.security.user.name=spring security username
    spring.security.user.password=spring security pass

    # Comment: if you don't have a database create it but if you have it update it.
    spring.jpa.hilbernate.ddl-auto=update

    # Comment: showing sql syntax
    spring.jpa.show-sql=true
    
# Spring JDBC



# Spring data JPA

    public interface ReviewRepository extends JpaRepository<Review, Long> {
        List<Review> findByMajorName(String majorName);
    }
    
    In JpaRepository interface, they ask for two things which class type are you working with and the what is a type of primary key.

    basic CRUD operations:

    **Save an Entity: <S extends T> S save(S entity);**

    E.X.: userRepository.save(newUser);
    
    **Find an Entity by ID: Optional<T> findById(ID id);**
    
    E.X.: Optional<User> user = userRepository.findById(userId);
    
    **Find All Entities List<T> findAll();**
    
    E.X.: List<Review> review = reviewRepository.findAll();

    **FOR UPDATE, DELETE SEARCH IT UP**

**Custom Query Methods**

1. Derived Query Methods

        public interface ReviewRepository extends JpaRepository<Review, Long> {
            List<Review> findByMajorName(String majorName);
        }

3. Custom JPQL/Native Queries

       @Query("SELECT u FROM User u WHERE u.lastName = ?1")
       List<User> findUsersByLastName(String lastName);
    
       @Query(value = "SELECT * FROM users WHERE last_name = ?1", nativeQuery = true)
       List<User> findUsersByLastNameNative(String lastName);

# Spring AOP

Spring AOP (Aspect-Oriented Programming) is a powerful feature in the Spring Framework that enables modularization of cross-cutting concerns, such as logging, security, transactions, and error handling, **without changing any code that in the core business logic of the application**.

Key Concepts of Spring AOP
1) Aspect: A module that encapsulates a cross-cutting concern. For example, a logging module can be an aspect.

2) Advice: The action taken by an aspect. It is the actual logic that is executed. Types of advice include:
- Before: Runs before the method execution.
- After: Runs after the method execution (regardless of its outcome).
- After Returning: Runs after a method successfully returns.
- After Throwing: Runs if a method throws an exception.
- Around: Runs both before and after the method execution, allowing more control over the behavior.

3) Join Point: A specific point in the application where advice can be applied, like method calls or object initialization.

4) Pointcut: A predicate that matches join points. Pointcuts help define where advice should be applied.

5) Weaving: The process of linking aspects with other application types or objects.

1) `@Aspect`
- Marks a class as an aspect, defining cross-cutting logic.
Example:

        @Aspect
        @Component
        public class LoggingAspect {
        
        	public static final Logger LOGGER=LoggerFactory.getLogger(LoggingAspect.class);
        	
        	
            // return type, class-name.method-name(args)
        	@Before("execution (* com.telusko.springbootrest.service.JobService.*(..))")
        	public void logMethodCall() {
        		LOGGER.info("Method Called ");
        	}
        }
     
2) `@Before`
Marks a method as advice that runs before a matched join point.
Example:

        @Aspect
        @Component
        public class LoggingAspect {
        
        	public static final Logger LOGGER=LoggerFactory.getLogger(LoggingAspect.class);
        	
        	
            // return type, class-name.method-name(args)
        	@Before("execution (* com.telusko.springbootrest.service.JobService.getJob(..)) || execution (* com.telusko.springbootrest.service.JobService.updateJob(..))")
        	public void logMethodCall(Joinpoint jp) {
        		LOGGER.info("Method Called " + jp.getSignature().getName());
        	}
   
        }

    
# Spring Security
