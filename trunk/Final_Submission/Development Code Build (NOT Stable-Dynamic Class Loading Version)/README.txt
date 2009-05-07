This is the latest development version of the project.  

It is meant to fulfill the requirement of dynamic class loading by 
allowing the user to load a new tool at runtime, and create new canvas
shapes when it is in use.

Please use the provided tools when testing:

AdvRectangle
AdvOval


These tools are identical to the ones within the program, just with 
different names to eliminate one source of error while developing.  


The class loading works fine if one user is connected.  You can create 
new shapes just as if they were the standard ones provided on the toolbar.
If more than one user is connected, and a tool is loaded and then used, 
it will crash the system.  
An exception occurs claiming that the object is not serializable.  
Both of the Adv classes above should implement serialization within their source code.
Until this issue is resolved, the passing of objects based off of dynamically loaded 
classes will not work.  This issue could easily have been resolved if more 
time was allocated towards this requirement.  



