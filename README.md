
# Olá, eu sou o Yago! 👋

  
# API de Votação 

O código contido nesse repositorio é sobre uma Rest API que gerencia votações no ambiente do cooperativismo. Solução back-end com endpoints de criação de: Associado, Pauta, Votação e Voto.



## API Reference

#### Acesso a aplicação

Para acessar a aplicação, após rodar o projeto, é necessario usar URL abaixo 
```http
  http://localhost:8080
```

#

#### Post para criar um novo Associado

```http
  POST /api/associado/cadastrar
```

| Parametro | Tipo     | Descrição                   |
| :-------- | :------- | :-------------------------    |
| `cpf`     | `string` | CPF do Associado sem separadores |
| `nome`    | `string` | Nome do associado                |

Nesse endpoint, o cpf passado é verificado para atestar se esta devidamente preenchido e se o nome do associado foi preenchido.

#

#### Post para criar uma nova Pauta

```http
  POST /api/pauta/criar_pauta
```

| Parametro       | Tipo     | Descrição                                                   |
| :-------------- | :------- | :---------------------------------------------------------- |
| questionamento  | `string` | Descrição da pauta para votação com minimo de 15 caracteres |

Nesse endpoint, é verificado se o questionamento esta vazio ou nulo.

#

#### Post cria um novo Voto

```http
  POST /api/voto/votar
```

| Parametro | Tipo     | Descrição                               |
| :-------- | :------- | :-----------------------------------    |
| `cpfAssociado`   | `string` | CPF do Associado sem separadores |
| `votoSimNao`     | `string` | O Voto de fato, como SIM ou NAO  |

Nesse endpoint, é verificado se existe uma votação em aberto para poder associar o voto, 
também é verificado se o Associado existe na base. Uma vez feita as verificações, é feita a associação do Voto à Votação. 

Existe uma etapa de verificação onde é consultada uma API de validação de CPFs que retorna se o 
Associado está apto a votar ou não mediante a consulta de API externa abaixo

```http
  https://user-info.herokuapp.com/users/{cpf}
```
  
#

#### Post cria uma nova Votação

```http
  POST /api/votacao/criar_votacao
```

| Parametro     | Tipo      | Descrição                      |
| :------------ | :-------- | :-------------------------     |
| `idPauta`     | `Integer` | Id da Pauta previamente criada |

Nesse endpoint, é verificado se a Pauta ja foi criada para assim fazer a associação a Votação

#### Post abrir uma Votação já existente

```http
  POST /api/votacao/abrir_votacao
```

| Parametro     | Tipo      | Descrição                                                             |
| :------------ | :-------- | :-------------------------------------------------------------------- |
| `idVotacao`   | `Integer` | Id da Votação previamente criada                                      |
| `tempo`       | `Integer` | Tempo em minutos que a votação devera ficar aberta. Ex: "2(minutos)"  |

Nesse endpoint, a votação fica aberta, conforme o tempo passado na requisição, 
de forma assincrona. Uma vez finalizado o tempo de da Votação, é realizado uma associação de todas as informações realizadas durante o tempo que 
a votação ficou aberta e assim persistindo a endidade de Votação final

Também é verificado se foi passado um valor de tempo, 
senão for, será considerado 1 minuto como tempo padrão. 

#

#### Documentação de EndPoints

A documentação dos endpoints, feita usando a linguagem de descrição de interface Swagger,
encontra-se no endereço URL

```http
  http://localhost:8080/api/swagger-ui/
```
#
#### Persistencia de Dados

A aplicação conta com um banco de dados em memoria, o H2, localizado na URL abaixo

```http
  http://localhost:8080/api/h2-console/login.jsp
```
E para acessar o console, é necessario utilizar os dados abaixo
| Usuario   | Senha    |
| :-------- | :------- |
| `admin`   | `root123`|

OBS.: Esse BD só pode ser acessado com a aplicação em funcionamento
## Authors

- [@yagovcb](https://www.github.com/Yagovcb)

  
## Tecnologias utilizadas

- Linguagem utilizada:
  ![Java](https://img.shields.io/badge/Java-ea2d2f?style=flat-square&logo=java&logoColor=ffffff)
- Framework e Ferramentas: 
  ![SpringBoot](https://img.shields.io/badge/SpringBoot-33CC00?style=flat-square&logo=springboot&logoColor=ffffff)
  ![Swagger2](https://img.shields.io/badge/Swagger2-33AC7C?style=flat-square&logo=swagger&logoColor=ffffff)
  ![JUnit5](https://img.shields.io/badge/JUnit_5-336600?style=flat-square&logo=junit5&logoColor=ea2d2f)
- Bibliotecas:
  ![ModelMapper](https://img.shields.io/badge/ModelMapper-3333CC?style=flat-modelMapper&logo=modelMapper&logoColor=ffffff)
  ![Lombok](https://img.shields.io/badge/Lombok-663300?style=flat-square&logo=lombok&logoColor=ffffff)

  
## Feedback

Se vocês tiverem algum feedback para me dar, porfavor é só mandar um email para: yago.vcb@hotmail.com

  
## 🔗 Links
[![Contact](https://img.shields.io/badge/yago.vcb@hotmail.com-FFFEEE?style=flat-square&logo=gmail&logoColor=red)](mailto:yago.vcb@hotmail.com)
[![Twitter](https://img.shields.io/badge/@Yagovcb-1DA1F2?style=flat-square&logo=twitter&logoColor=white)](https://twitter.com/Yagovcb)
[![Linkedin](https://img.shields.io/badge/Yago_do_Valle_Castelo_Branco-0077b5?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/yagovcb/)

  
## Appendix

Caros amigos, sintam-se a vontade para criar PR's com melhorias ou acrescimos para esse Projeto!

  