# Spring framework V.S. Spring boot 

Spring framework provides comprehensive infrastructure support for developing Java applications. 

Spring Boot is basically an extension of the Spring framework, which eliminates the boilerplate configurations required for setting up a Spring application.

# Spring framework concept

**Dependency Injection (DI)** is a design pattern where an object (or class) receives its dependencies from an external source rather than creating them itself. 
In Spring, the ***IoC (Inversion of Control)** Container is responsible for managing the objects' lifecycle and injecting dependencies when needed.

A **bean** is simply an object that is created, 
assembled, and managed by Spring's IoC container. The Spring IoC container is responsible for:

- Creating bean instances.
- Managing dependencies between beans (Dependency Injection).
- Managing the lifecycle of beans (initialization and destruction).


1)
- `@Component`: A generic stereotype annotation to mark a class as a Spring-managed bean.
- `@Service`: A specialization of `@Component`, used to indicate that the class contains business logic.
- `@Repository`: A specialization of `@Component`, used to indicate that the class deals with data access (like interacting with databases).
- `@Controller`: A specialization of `@Component`, used to mark a class as a web controller that handles HTTP requests.

2) Using `@Bean` in a `@Configuration` Class
You can explicitly define beans using the `@Bean` annotation inside a configuration class. 
This gives you **more control over the bean creation process**

**Configuration Class**: It is used to define beans explicitly using Java methods. The methods inside a @Configuration class annotated with @Bean will be used to create Spring beans.
**Bean Definition**: Each method annotated with @Bean within a @Configuration class represents a bean definition. The Spring container manages these beans like it does for beans created via @Component.

3) **Automatic Bean Detection** with `@SpringBootApplication` 
When you annotate your main application class with `@SpringBootApplication`, it enables component scanning automatically. 
This will scan the package where the main class is located and all sub-packages for components (like `@Component`, `@Service`, `@Repository`, etc.) 
and automatically register them as beans.

4) `@Autowired`
- Is used for Dependency Injection (DI) in Spring.
- It can be applied to fields, constructors (recommended), or setter methods.
- It tells Spring to inject the appropriate bean from the IoC container into the target class.
- Constructor injection is preferred for better immutability, testability, and clarity.

When to Use `@Autowired`?
**In Services**: When you need to inject another service or repository into your service layer.
**In Controllers**: When injecting service beans to handle business logic.
**In Tests**: When you need to automatically inject beans for testing.

## Spring data JPA



## Security
