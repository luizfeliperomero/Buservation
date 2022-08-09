# Buservation

Para renderizar os arquivos html e a escrita no log ser feita no arquivo já existente, é necessário mudar o caminho da variável file no arquivo "src/main/java/webServer/ReserveRunnable.java", linha 89,  para o
caminho absoluto da pasta onde estão os arquivos html da sua máquina, no arquivo "/src/main/java/Log/Log.java", linha 30, também é necessário mudar o caminho da variável file para o caminho absoluto do arquivo de log.
