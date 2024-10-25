Bom, o aplicativo consiste em 3 telas:
- Tela principal.
- Tela com detalhes do filme.
- Tela para pesquisar os filmes.

## Tela principal:
  - Na tela principal, temos dois botões, um sendo para o usuário ver as últimas estreias no cinema brasileiro, e outra botão onde você consegue ver os filmes que irão lançar em breve.
  - Os filmes que irão lançar em breve, tem sua data de estreia no poster.
  - Os filmes que estão em pré venda, são rodeados com uma borda dourada e uma caixinha dentro escrito "Pré-venda".
  - Ao apertar em qualquer filme, você irá para uma tela com os detalhes do filme clicado.

<img src="https://github.com/user-attachments/assets/0620d340-2820-4d3a-9033-612157be87d4" width="200"/>
<img src="https://github.com/user-attachments/assets/1c0eae8b-57a7-4bcb-9f59-e018c52e7f34" width="200"/>


## Tela com detalhes do filme:
  - Cada filme tem sua tela de detalhes, esses detalhes são:
  - Caso o filme tenha trailer, tem um botão de Start que te leva para o trailer no Youtube.
  - Poster do filme, caso não tenha, é uma tela em cinza escrito "Sem poster".
  - Nome do filme.
  - Classificação indicativa.
  - Gênero do filme.
  - Diretor.
  - Atores.
  - Sinopse.

    <img src="https://github.com/user-attachments/assets/d7faefce-b618-4055-a874-a0be9935ec13" width="200"/>

## Tela para pesquisar os filmes:
  - Na tela de pesquisa, temos uma caixa onde você digita o filme que você quer ou o ator, pesquisando o ator, irá aparecer os filmes desse ator que estão ali.
  - Pesquisa em tempo real, cada letra colocada na caixa vai aparecendo os filmes que você deseja chegar.

    <img src="https://github.com/user-attachments/assets/665ae30e-6172-4e89-a71e-4a263fd38f01" width="200"/>
    <img src="https://github.com/user-attachments/assets/f699c5ed-f18b-47c7-9026-6e8df9597a57" width="200"/>

## Detalhes Técnicos:
  - Utilizei a arquitetura MVVM para poder separar as responsabilidades e para fácil manutenção futura.
  - Utilizei o Framework Jetpack Compose.
  - Utilizei a biblioteca do LiveData para poder passar os dados em tempo real para a UI. Site(https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br)
  - Utilizei a biblioteca Coil para carregar as imagens de forma assincrona, para não sobrecarregar a UI e respeitar o tempo de vida do aplicativo. Site(https://github.com/coil-kt/coil)
  - Utilizei a biblioteca RetroFit para poder pegar os dados da API de forma simples e fácil. Site(https://square.github.io/retrofit/)
## Como faço para poder rodar esse aplicativo em meu dispositivo?
  - Caso baixe o aplicativo, em Releases aqui no Github, tem o apk para baixar o aplicativo e testar.
