# Slurp

A Java task runner, with a lean feature set.

## Usage

### Create a SlurpTask

`SlurpTask`s are Java records. They have a `name` and a `runnable` property. Neither the `name` nor `runnable` property may be null.
You can create a `SlurpTask` like so.

```java
SlurpTask printHelloWorld = new SlurpTask("HelloWorld", () -> System.out.println("Hello World!"));
```

### Register and Execute a SlurpTask

Inorder to register a `SlurpTask` for excution a `Slurp` instance is needed. The `Slurp` instance allows for tasks to be registered and
executed.

```java
Slurp slurp = new Slurp();
SlurpTask printHelloWorld = new SlurpTask("HelloWorld", () -> System.out.println("Hello World!"));

slurp.registerTask(printHelloWorld);
slurp.executeTask(printHelloWorld);
```

Task names must be unique, if a duplicate task name is registered a `DuplicateTaskException` will be thrown. If an attempt is made to execute
an unknown task a `TaskNotFoundException` will be thrown.

### Executing a sequence of tasks

`Slurp` can be used to execute a sequence of tasks. Internally it uses a single threaded executor, and waits for each task to finish before
executing the next.

```java
String[] sendMarketingEmail = new String[] {
  "prepareClientList",
  "generateEmailHTML",
  "sendSpamMarketingEmail"
};

slurp.executeTaskSequence(sendMarketingEmail); // may also take a SlurpTask[]
```
