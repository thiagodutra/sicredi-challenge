# sicredi-challenge

# Objetivo
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias,
por votação. Imagine que você deve criar uma solução backend para gerenciar essas sessões de
votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de
uma API REST:
Cadastrar uma nova pauta
Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um
tempo determinado na chamada de abertura ou 1 minuto por default)
Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado
é identificado por um id único e pode votar apenas uma vez por pauta)
Contabilizar os votos e dar o resultado da votação na pauta

# Para rodar a aplicação:

- `docker-compose up`

# Utilizando o Postman
- Importe a collection que se encontra na pasta postman_collection

- Siga a ordem:

Agenda:
  - Create Agenda
  - Create Voting Session
  `Utilize o id da agenda`
  
Vote:
  - Do Vote
  `Utilize o id da VotingSession.`
