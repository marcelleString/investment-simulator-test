### Projeto Automação de Testes Funcionais de Simulação de Investimento de Poupança

### Tecnologias e Ferramentas utilizadas:
>Java
>Gradle (Gerenciamento de Dependências)
>Junit (Execução de Testes de forma organizada, Assertions, Relatórios de Teste)
>Log4J (Relatóriosde Execução)
>RestAssured (Comunicação com Serviços, Requests e Responses)
>Selenium Webdriver (Comunicação com Interface Gráfica, Elementos)

##DataProvider (Nesta pasta é possível criar dados de entrada e saída, para simular todo tipo de Investimento de Poupança):
>investment-simulator-test\src\test\resources\data

##Relatório dos testes (Após execução dos testes exibe inofrmações detalhadas):
>investment-simulator-test/build/reports/tests/test/packages/default-package.html

##Logs de Execução:
>investment-simulator-test\evidence\logs

### Configurações
Antes de rodar o projeto deverão ser instaladas as ferramentas abaixo e configuradas suas variáveis de ambiente:
>JDK 1.8.0_201;
>Gradle 5.2.1;

### Desenvolvimento
Para desenvolvimento do projeto e execução prévia, poderá ser utilizada a ferramenta: IntelliJ IDEA Community.
É necessário importar a pasta do projeto com Gradle (auto-import).

### Execução
>**gradle clean test**

###Cenários Testados:
#SERVIÇOS:
GET/Simulador - Validacao de Valores Fixos dos Campos
GET/Simulador - Validacao de Formato de Resposta: Campos e Tipos de Dados
GET/Simulador - Validacao de Status Code

#UI:
Validar Valores de Simulacao de Investimento de Poupanca : Perfil: paraVoce, ValorAplicar: 50,00, ValorInvestir: 30,00, Tempo: 1A	validateSavingsInvestmentSimulation(ArgumentsAccessor)[1]	14.756s	passed
Validar Valores de Simulacao de Investimento de Poupanca : Perfil: paraVoce, ValorAplicar: 500,00, ValorInvestir: 200,00, Tempo: 6M	validateSavingsInvestmentSimulation(ArgumentsAccessor)[2]	15.740s	passed
Validar Valores de Simulacao de Investimento de Poupanca : Perfil: paraEmpresa, ValorAplicar: 2.000,00, ValorInvestir: 1.000,00, Tempo: 5A	validateSavingsInvestmentSimulation(ArgumentsAccessor)[3]	15.412s	passed
Validar mensagem de erro quando informado valor menor que R$20.00 em ValorAplicar e ValorInvestir

###História
Com o propósito de Simular um Investimento na Poupança
como um Associado, eu gostaria de preencher o formulário de simulação
e ver a tabela de resultado com Mês e Valor.

>Critério de aceitação:
1. O associado preencher todos os campos corretamente e visualizar o formulário de simulação
2. O associado preencher o valor inferior a “R$ 20.00” e receber a mensagem de orientação “Valor mínimo de R$ 20.00”.

#UI Url: https://www.sicredi.com.br/html/ferramenta/simulador-investimento-poupanca/

#API Url: GET http://5b847b30db24a100142dce1b.mockapi.io/api/v1/simulador
Response:
{
 "id": 1,
 "meses": ["112", "124", "136", "148"],
 "valor": ["2.802", "3.174", "3.564", "3.971"]
}