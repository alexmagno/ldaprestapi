# Desafio Técnico - ldaprestapi

Este projeto é referente ao teste técnico da vaga #GDP-436

Para configurar o OPENLDAP Server, é preciso seguir as instruções passadas no desafio:

# OPENLDAP Server

Description:

Install OpenLDAP Server.

We recommend using this image: https://github.com/osixia/docker-openldap. But feel free to use any other image you want.

Config:

Domain: techinterview.com

Under your domain create an ou=Users that will hold inetOrgPerson to represent the user accounts



LDAP CONFIGURATION (Additional info Only for the full stack position):

Running ldap container:


docker run --env LDAP_ORGANISATION="My Company" --env LDAP_DOMAIN="techinterview.com" --env LDAP_ADMIN_PASSWORD="123456" -p 389:389 --detach osixia/openldap:1.3.0


Create the OU user under domain:
Create the file create_ou_users.ldif with the following content:

dn: ou=Users,dc=techinterview,dc=com

changetype: add

objectClass: organizationalUnit

objectClass: top

ou: Users

<blank line at the end of the file>


The following command will create a OU(organizationalUnit) to hold your users:

ldapmodify -h localhost -p 389 -w '123456' -D 'cn=admin,dc=techinterview,dc=com'  <  create_ou_users.ldif

# API

Para rodar o projeto é necesário baixá-lo e importá-lo, preferencialmente na IDE Intellij Idea.

Apoś o projeto ser importado, basta executar a classe com.technicalinterview.ldaprestapi.LdapRestApiApplication.

Depois de executado, para acessar a documentação da API gerada pelo Swagger, é necessário acessar o endereço http://localhost:8080/swagger-ui.html.

Para rodar os testes, basta executar a classe com.technicalinterview.ldaprestapi.LdapRestApiApplicationTests.
