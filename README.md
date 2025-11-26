# Users App

Aplicativo Android nativo desenvolvido em Kotlin que permite visualizar, buscar e gerenciar uma lista de usuários consumindo dados de uma API REST.

## Tempo estimado de desenvolvimento
- **26 horas**
  
## Funcionalidades

### Lista de Usuários
- **Visualização de Usuários**: Exibe uma lista completa de usuários com nome e email
- **Busca em Tempo Real**: Campo de pesquisa que filtra usuários por nome ou email
- **Paginação Automática**: Carrega mais usuários conforme você rola a lista
- **Pull-to-Refresh**: Arraste para baixo para atualizar a lista
- **Estado de Loading**: Indicador visual durante carregamento
- **Tratamento de Erros**: Mensagens claras quando algo dá errado
- **Persistência Local**: Dados salvos no banco de dados Room para acesso offline

### Detalhes do Usuário
- **Informações Pessoais**: Nome, username, email, telefone e website
- **Endereço Completo**: Rua, complemento, cidade, CEP e coordenadas geográficas (latitude/longitude)
- **Dados da Empresa**: Nome da empresa, slogan e área de atuação
- **Interface Organizada**: Informações divididas em cards para melhor visualização
- **Scroll Vertical**: Navegue por todas as informações do usuário

##  Instruções de Execução

### Configuração do Projeto

1. **Clone o repositório**
```bash
git clone https://github.com/ErickGFerreira/Users.git
cd users
```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta do projeto
   - Aguarde a sincronização do Gradle

3. **No Android studio clique em Run**


