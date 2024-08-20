# Ejecuar el siguiente comando para ignorar achivos modificados localmente y no sean tomados en cuenta al subirlos en github

## en este caso ignoramos los cambios locales del archivo application.properties
`git update-index --assume-unchanged src/main/resources/application.properties`

## comanto para verificar estado
 `git ls-files -v | grep '^[[:lower:]]'`
