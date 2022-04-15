# Api Avaliativa Broadfactor
## Tecnologias utilizadas

- Java 11
- Spring Boot 2.5.5
- Retrofit2
- Lombok
- Docker
## Etapas para configurar o aplicativo Spring Boot Back end (broadfactor-app-server)

1. **Clonar o aplicativo**

	```bash
	git clone https://github.com/leandropedrosa/api_broadfactor.git
	cd broadfactor-app-server
	```

2. **Docker**

	```bash
	mvn clean package
	```
	Subir os continentes
	```bash
	docker-compose up
	```
	Para derrubar os continentes
	```bash
	docker-compose down
	```

3. **Criar banco de dados MySQL**

	```bash
	create database broadfactor_app
	```

4. **Altere o nome de usuário e a senha do MySQL de acordo com sua instalação do MySQL**

	+ abra o arquivo `src/main/resources/application.properties`.

	+ mude `spring.datasource.username` e `spring.datasource.password` propriedades de acordo com sua instalação do mysql

5. **Execute o aplicativo**

	Você pode executar o aplicativo de inicialização de mola digitando o seguinte comando -

	```bash
	mvn spring-boot:run
	```

	O servidor iniciará na porta 8080.

	Você também pode empacotar o aplicativo na forma de um arquivo `jar` e, em seguida, execute-o assim -

	```bash
	mvn package
	java -jar target/polls-0.0.1-SNAPSHOT.jar
	```
6. **Perfis Padrão**
	
	O aplicativo de inicialização por mola usa autorização baseada em função alimentada pela segurança de mola. Para adicionar as funções padrão no banco de dados, adicionei as seguintes consultas sql em arquivo `src/main/resources/data.sql`. Spring boot executará automaticamente este script na inicialização -

	```sql
	INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
	INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
	```

	Qualquer novo usuário que se inscreva no aplicativo recebe o `ROLE_USER` como padrão.

Mais informações
----------------

**Leandro Pedrosa**
- Meu [Linkedin](https://www.linkedin.com/in/leandro-p-a28291103/)
- Email: leandropedrosalp@gmail.com
