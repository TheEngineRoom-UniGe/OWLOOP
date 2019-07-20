##############################################################################
# CMake
##############################################################################

cmake_minimum_required(VERSION 2.8.3)
project(owl_oop)

##############################################################################
# Catkin
##############################################################################

find_package(catkin REQUIRED rosjava_build_tools multi_ontology_reference)

# Set the gradle targets you want catkin's make to run by default, e.g.
#   catkin_rosjava_setup(installApp)
# Note that the catkin_create_rosjava_xxx scripts will usually automatically
# add tasks to this for you when you create subprojects.
catkin_rosjava_setup(install publishMavenJavaPublicationToMavenRepository)

catkin_package()

##############################################################################
# Installation
##############################################################################

# Change this to match the maven group name you have specified in the 
# allprojects closure the root build.gradle
#install(DIRECTORY ${CATKIN_DEVEL_PREFIX}/${CATKIN_GLOBAL_MAVEN_DESTINATION}/it/emarolab/${PROJECT_NAME}/ 
#        DESTINATION ${CATKIN_GLOBAL_MAVEN_DESTINATION}/it/emarolab/${PROJECT_NAME})

#fix of the above command for supporting `catkin_make install` 
#(thanks Taha Elsenosy for suggesting this correctionon in the issue: https://github.com/EmaroLab/multi_ontology_reference/issues/6)
install(DIRECTORY ${CATKIN_DEVEL_PREFIX}/share/${PROJECT_NAME}/ 
       DESTINATION ${CATKIN_DEVEL_PREFIX}/share/${PROJECT_NAME})
