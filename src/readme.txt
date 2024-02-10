Sistema de notificação de mensagens.

Descritivo conteúdo pasta resources:

observe-me.txt: O arquivo "observe-me.txt" representa o objeto que será observado pelos clientes.
    Quando o objeto observado for alterado, ou seja, quando ocorrer uma mudança em seu estado(open,
    save ou delete), será emitido uma mensagem para os observadores(obsevadoresList).

peopleList.txt: O arquivo peopleList.txt contém a lista de usuários responsáveis por alterar o
    objeto.

observadoresList.txt: Este arquivo contém a lista das pessoas(observadores) que tem interesse em
    receber notificações sobre o estado do objeto.



Aplicação principal:
    Para rodar o sistema de notificação de mensagens, rode a aplicação principal, RunApplication.
    As mensagens mostradas no console ao executar a apllicação, simulam as mensagens/notificaçações enviadas.
