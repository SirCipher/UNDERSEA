.. _logging:


Implementing a new service
==================

To demonstrate implementing a new service, this guide details implementing a new logging service. In most applications, it may be more desirable to utilise a persistent storage medium as opposed to an in-memory one. This tutorial details implementing a simple service that will achieve this functionality.


Services in UNDERSEA implement the :code:`AgentService` interface and in turn, this interface extends from :code:`AgentAware`. While creating a new service, the initialisation process may require a reference to the associated agent instance, however, this has not been fully constructed yet. The :code:`AgentAware` interface solves this by moving initialisation process of a service to the :code:`initialise` method that must be implemented and this provides a reference to the agent.

Lets start by creating a new :code:`DiskLogService` that contains a :code:`BufferedWriter`

.. code-block:: java

    public class DiskLogService implements LogService {
        
        private Agent agent;
        private BufferedWriter writer;
    
        @Override
        public void initialise(Agent parentAgent) {
            this.agent = parentAgent;
            try {
                this.writer = new BufferedWriter(new FileWriter("log-" + agent.name() + ".txt", true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    ...

The :code:`initialise` method is called by the :code:`ServiceManager` during starting a service before calling the :code:`run` method on the service. This ensures that every service is fully initialise beforehand.

Now we can implement the (very simple) :code:`add` method so log entries are written:

.. code-block:: java

    @Override
    public void add(LogEntry logEntry) {
        try {
            writer.write(logEntry.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

The :code:`add` method can be invoked directly on the instance of the :code:`DiskLogService` or, normally, by acting directly on the instance of an agent through :code:`agent.log(logEntry)`. To register the new logging service with the :code:`ServiceManager`:

.. code-block:: java

    serviceManager.registerService(new DiskLogService());
