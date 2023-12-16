FROM openjdk:17-oracle
COPY target/*.jar category_tree_manager_bot.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","category_tree_manager_bot.jar"]