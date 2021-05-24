A maior parte do meu esforço técnico foi direcionada para a [implementação](https://github.com/canelhasmateus/cars). <br> O texto foi feito de uma forma mais lúdica para leigos.

# <div style="text-align: center;">``Ato 1``</div>

> <br> - Môr.
> <br><br> - Hm.
> <br><br> - Moooor.
> <br><br> - Hmmmmmmmmmmm
> <br><br> - Cê não tá me ouvindo não?!
> <br><br> - Já te respondi pessoa, diz o que precisa . . .
> <br><br> - Como que o iFood sabe que eu pedi comida?
> <br><br> - Cê tá pensando demais denovo né kkkkkkkkkkkk Seu celular manda o pedido através da internet uai
> <br><br> - Esse tanto eu sei né, queria entender um pouco mais a fundo. Não é isso que vocês fazem no trabalho? Mexer com internet?
> <br><br> ``< Sarcasticamente >`` Claro que não. A gente formata computador também.
> <br><br> - Engraçadinho. Agora me explica como funciona.

![dialogo.png](dialogo.png)

> <br> - Certo. Assim como em outros aspectos da vida, tudo se resolve na conversa. Pra computadores também acontece a mesma coisa. Olha esse diagrama aqui:
> <br><br> - Via de regra, na internet, tanto o locutor quanto ouvinte são ambos computadores. Sobra a linguagem e o assunto.
> <br><br> - Certo...
> <br><br> - A parte da linguagem não é interessante te explicar agora, mas saiba que existem algumas mais padrão, tipo HTTP. A parte legal é o assunto.
> <br><br> - Como assim?
> <br><br> - Bom, enquanto a skynet não for uma realidade, computadores tem que ser programados pra exercerem as funções que eles fazem certo? Então, eles so conseguem entender algumas coisas pre-definidas. Então a gente cria um manual dos assuntos e das formas que você pode conversar com ele.
> <br><br>- Ainda não entendi.
> <br><br>- Aqui um exemplo ó. Tá vendo essa listinha? É um manual de todas as coisas que você pode conversar com esse computador, e as coisas que ele pode te responder.
![cardapio.png](cardapio.png)
> <br> - Parece um cardápio.
> <br><br> - Você só pensa comida né. Por sorte, essa é uma analogia muito boa, então vou continuar nela. Pensa dessa forma: Quando você vai num restaurante, não dá muito certo você pedir algo fora do cardápio, certo? Eles provavelmente não vão conseguir te atender.
> <br><br> - Entendi. Mas fiquei com uma dúvida.
> <br><br> - Diga.
> <br><br> - O ouvinte é sempre mais feinho mesmo?
> <br> <div style="text-align: center;"> ``< silêncio >`` </div><br>

# <div style="text-align: center;">``Ato 2``</div>

> <br> - Então, é isso. A gente chama essa junção de computadores + cardápio de API. Na analogia do restaurante, seria tipo um balcão. Seu celular faz uma chamada no balcão, avisando que você
> pediu tal coisa.
> <br><br> - Tipo um garçom?
> <br><br> - Na real que sim. Essa analogia do restaurante ta sendo mais completa do que eu imaginava.
> <br><div style="text-align: center;">``< Risos sociais >``</div>
> <br>- E tem diferença, uma API das outras? Igual a gente vê em restaurante?
> <br><br> - ô, se tem. O que você espera encontrar num restaurante? E o que você leva em conta pra saber se você vai voltar?
> <br><br> - Ah, espero que tenha algumas mesas, pra gente sentar e comer. Comparo atendimento, se é bom e rápido . . . Tem também o ambiente, se é limpinho, aconchegante.
> <br><br> - É. A gente tem um conceito bem parecido. O mais comum é que uma API, quando é construída, tenha 3 "camadas": A camada de apresentação lida com os clientes, só repassando pedido - Tipo
> um garçom. A gente também costuma ter uma camada de processamento, que é onde a gente faz o que é preciso pra atender seu pedido. Seria a cozinha.
> <br><br> - Ta. e a 3a?
> <br><br> - Não sei bem como que ela encaixa nessa analogia. Ela é de persistência, que é onde a gente salva dados. . .
> <br><br> - Tipo a geladeira , e o freezer? Um estoque, onde você guarda o que é importante pra você conseguir atender os pedidos depois?
> <br><br> - É. Algo do tipo. A parte importante é que exista a maior separação possível entre essas áreas. Você não quer que a pessoa que faz seu prato também seja a pessoa que devolve troco no
> caixa sabe? Não  que não funcione, só é bem anti-higiênico. E ineficiente também.
> <br><br> - Entendi. esse nível de organização varia de restaurante pra restaurante. Imagino que a analogia continua?
> <br><br> - Claro. E a diferença é entre o master chef e um pesadelo na cozinha.<br>

# <div style="text-align: center;">``Ato 3``</div>

> <br> - Só pra ficar ainda mais claro, dá uma olhada nisso aqui.
> <br><div style="text-align: center;">``< Magicamente digita o programa do repositório no tempo entre as duas sentenças>``</div>
> <br> - Nossa, foi muito rápido. É facil assim?
> <br><br> - Ah, nem tanto. Acaba sendo mais rápido porque existem ferramentas que ajudam nessa construção. A gente chama de frameworks.
> <br><br> - E qual a analogia disso com o restaurante?
> <br><br> - Aí você me apertou sem abraçar. Mas o que eu queria te mostrar era essa parte. Esse aqui é um programa que a gente usa pra conversar com a API.
> <br><br> - Achei bonitinho. Parece fácil de usar.
> <br><br> - É sim.
> <br><br> - Essa listinha aí é dos itens que a gente pode pedir?
> <br><br> - Isso mesmo. Vou fazer uma simulação aqui pra você:


![cadastro.png](cadastro.png)
<div style="text-align: center;"> A gente faz um cadastro,</div>

![login.png](login.png)
<div style="text-align: center;"> Depois um login,</div>

![veiculo.png](veiculo.png)
<div style="text-align: center;">- Aí conseguimos registrar carros no cadastro,</div>

![listagem.png](listagem.png)
<div style="text-align: center;">- E ler eles denovo, juntamente com algumas informações adicionais, tipo o preço dele na tabela FIPE.</div>  

> <br><br> - E pra que serve?
> <br><br> - Honestamente, essa aqui é mais didatica mesmo. Só um exemplo de como que APIs podem conversar entre si.
> <br><br> - Então a gente pode ter uma API chamando outra?
> <br><br> - Claro. A gente consome o trabalho de outras pessoas o tempo todo. Da mesma forma, um restaurante também. Imagina se pra todo pedido eles tivessem que plantar e colher os ingredientes?
> <br><br> - Eles não fazem isso? Pelo tempo que demora, achei que fosse esse o caso.

> <br> - Engraçadinha. Mas sanou suas duvidas?
> <br><br> - Ficou um pouco mais claro sim.
> <br><br> - Que bom. Fico até surpreso, que você tenha entendido tão fácil e rápido.
> <br><br> - É porque sou muito inteligente e esforçada, e definitivamente não um figmento da sua imaginação, cumprindo a trope de proxy de exposição pra um texto de processo seletivo.
> <br><br> - Faz todo o sentido.
> <br><br> - Inclusive, falando em trabalho, vou precisar de uma ajuda com aquela segunda parte que você faz.
> <br><br> - Segunda parte? Como assim?
> <br><br> - Ah, meu notebook tá meio lento. Formata pra mim?