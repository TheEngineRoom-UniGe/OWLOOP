# ROS-Java template package

This repository contains a simple template for ROS packages that use the Java programming language.

It depends on ros-java, installable o via `apt-get`  (tested with Ubuntu 16.04 and ROS kinetic

It use the arMOR library as a placeolder to show how to include jars; checkout the gradle file.

Remember to put all the java messages in a separate package.

### Usefull commands

- `./gradlew install` (and `installApp` automatically called from catking)
- `./gradlew deployApp` (per usre il roslaunch)
- `./gradlew publish` (used if you make rosjava libraries)

### Node and Service Examples

Checkout this code for examples:
- [Publisher](https://github.com/guiklink/ME495_Rosjava_Startup/blob/master/my_pub_sub_tutorial/src/main/java/com/github/rosjava_catkin_package_a/my_pub_sub_tutorial/Talker.java)
- [Subscriber](https://github.com/guiklink/ME495_Rosjava_Startup/blob/master/my_pub_sub_tutorial/src/main/java/com/github/rosjava_catkin_package_a/my_pub_sub_tutorial/Listener.java)
- [Service](http://wiki.ros.org/rosjava_build_tools/Tutorials/indigo/UsingServices)

### For ROSrun or ROSlauch

call `deployApp` first, than use:
``
 rosrun <ros_package_name> execute <java.path.to.node.or.Service>
``
or for roslauch
``
 <node pkg="ros_package_name" type="execute" name="ros_package_name" args="java.path.to.node.or.Service"/>
``

### Messages

If you want to use custom message, which depends from many packages in your warkspace,is always god to have a separate packages for that. Please check the `ros_java_msgs` for an example.

### Author

[Luca Buoncompagni](mailto:luca.buoncompagni@edu.unige.it)
EMAROlab, DIBRIS department, University of Genoa, Italy.

Previous commit for this package can be [found here](https://gitlab.com/buoncubi/ros_java_template_pkg).
