package com.example.data.repository

import com.example.data.model.FloorPlanCategory
import com.example.data.model.FloorPlanPoint
import com.example.data.model.LocalRecommendation
import com.example.data.model.LocationCategory
import com.example.data.model.ManualSection
import com.example.data.model.MapLocation
import com.example.data.model.RoomItem
import com.example.data.model.RoomPhoto
import com.example.data.model.SectionItem

object HouseRepository {

    val HOUSE_TITLE = "Solarium. Casa ampla com vista panorâmica"
    val AIRBNB_URL = "https://www.airbnb.com.br/rooms/1143502588915376302"
    val HOUSE_ADDRESS = "Rua Presidente Castelo Branco, 95, Ramon, São Lourenço - MG"
    val HOUSE_REFERENCE = "Primeira rua logo atrás da Pousada Le Sapé"
    val WIFI_SSID = "ValeriaMR"
    val WIFI_PASSWORD = "pazeamor"
    val CHECKIN_TIME = "A partir das 13:00"
    val SILENCE_HOURS = "22:00 às 07:00"
    val HOST_NAME = "Valéria"
    val HOST_PHONE = "5521996917799" // Telefone / WhatsApp da anfitriã Valéria (+55 21 99691-7799)
    val HOST_PHONE_DISPLAY = "(21) 99691-7799"

    val sections: List<ManualSection> = listOf(
        // (1) Bem Vindo
        ManualSection(
            id = 1,
            title = "Bem-vindos",
            subtitle = "Sua estadia aconchegante em São Lourenço",
            shortSummary = "Seja muito bem-vindo! Esperamos que sua estadia seja incrível e revigorante.",
            iconName = "Favorite",
            accentColorHex = 0xFFC85A32,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/af2bb15c-883e-4228-babe-c15897967312.jpeg",
            roomCategory = "Fachada & Solarium",
            items = listOf(
                SectionItem(
                    title = "Mensagem da Anfitriã",
                    description = "Olá! Preparamos este espaço com muito carinho para você e sua família relaxarem nas montanhas de Minas Gerais. Este manual digital reúne tudo o que você precisa saber para aproveitar a casa e a cidade com total conforto e tranquilidade."
                ),
                SectionItem(
                    title = "Acesso Rápido ao Wi-Fi",
                    description = "Rede: $WIFI_SSID | Senha: $WIFI_PASSWORD",
                    badge = "Conexão Imediata"
                ),
                SectionItem(
                    title = "Contato & Suporte",
                    description = "Qualquer dúvida durante a estadia, você pode falar diretamente com a proprietária Valéria pelo WhatsApp $HOST_PHONE_DISPLAY ou usar a aba de Concierge Digital.",
                    badge = "WhatsApp: $HOST_PHONE_DISPLAY"
                )
            ),
            tips = listOf(
                "Mantenha este guia sempre à mão para tirar dúvidas sobre eletrodomésticos, chaves e passeios.",
                "São Lourenço é famosa por seu ar puro e águas termais benéficas para a saúde."
            )
        ),

        // (2) Localização - 📍
        ManualSection(
            id = 2,
            title = "Localização",
            subtitle = "Rua Presidente Castelo Branco, 95 - Ramon",
            shortSummary = "Primeira rua atrás da Pousada Le Sapé em São Lourenço - MG.",
            iconName = "Place",
            accentColorHex = 0xFF2D6A4F,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/95fcbad5-bd73-4fa6-bc8c-fd1f437b9078.jpeg",
            roomCategory = "Fachada & Bairro",
            items = listOf(
                SectionItem(
                    title = "Endereço Completo",
                    description = HOUSE_ADDRESS,
                    badge = "GPS & Chegada"
                ),
                SectionItem(
                    title = "Ponto de Referência Principal",
                    description = "A casa fica localizada exatamente na primeira rua atrás da Pousada Le Sapé. Um local calmo, seguro e de fácil acesso no bairro Ramon."
                ),
                SectionItem(
                    title = "Como Chegar",
                    description = "Ao se aproximar da Pousada Le Sapé, contorne a quadra para entrar na primeira rua de trás (Rua Pres. Castelo Branco). O número 95 possui portão eletrônico e muro privativo."
                )
            ),
            tips = listOf(
                "Você pode clicar no botão de mapa para traçar rotas direto pelo Waze ou Google Maps.",
                "O bairro Ramon é muito tranquilo, perfeito para caminhadas matinais."
            )
        ),

        // (3) Chegada / Check-in
        ManualSection(
            id = 3,
            title = "Chegada / Check-in",
            subtitle = "Horário, chaves, portão e senha do Wi-Fi",
            shortSummary = "Check-in a partir das 13h, chaves e dica da porta antiga.",
            iconName = "Key",
            accentColorHex = 0xFFB87010,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/cc042dc9-b8d6-4086-b4ab-641878a23f2d.jpeg",
            roomCategory = "Entrada & Portão",
            items = listOf(
                SectionItem(
                    title = "Horário de Check-in",
                    description = "O check-in está liberado a partir das 13:00 horas.",
                    badge = "A partir das 13h"
                ),
                SectionItem(
                    title = "Rede Wi-Fi",
                    description = "Nome da rede: $WIFI_SSID\nSenha: $WIFI_PASSWORD",
                    badge = "Internet Rápida"
                ),
                SectionItem(
                    title = "Conjunto de Chaves",
                    description = "No chaveiro há:\n• Uma chave para a porta de entrada principal\n• Uma chave para a porta dos fundos\n• Uma chave para o portãozinho de pedestres\n• Chave preta: Apenas RESERVA para o portão eletrônico (caso falte energia ou o controle não funcione)."
                ),
                SectionItem(
                    title = "Porta de Entrada da Casa (Importante!)",
                    description = "É uma porta antiga charmosa. Para conseguir abri-la com facilidade, deve-se puxar a porta suavemente para fora com uma mão enquanto gira a chave com a outra.",
                    badge = "Dica Essencial"
                ),
                SectionItem(
                    title = "Portão Eletrônico (Carros)",
                    description = "Para abrir e fechar o portão de carros, aperte o botão superior direito do controle remoto.",
                    badge = "Controle Remoto"
                )
            ),
            tips = listOf(
                "Lembre-se de sempre conferir se o portão eletrônico fechou completamente após guardar o carro.",
                "A chave preta deve ser guardada com cuidado e utilizada somente em emergências."
            ),
            warnings = listOf(
                "Não force a chave na porta antiga da sala; basta puxar a folha da porta para fora com a outra mão enquanto gira suavemente a chave."
            )
        ),

        // (4) Entretenimentos na Casa
        ManualSection(
            id = 4,
            title = "Entretenimento na Casa",
            subtitle = "Pátio, rede, piano, espreguiçadeiras e TV",
            shortSummary = "Espaço ao ar livre, relaxamento e passo a passo dos controles da TV.",
            iconName = "Tv",
            accentColorHex = 0xFF5856D6,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/901c8a9c-e1d9-42d7-b61e-96363e9681eb.jpeg",
            roomCategory = "Sala de Estar & TV",
            items = listOf(
                SectionItem(
                    title = "Pátio e Varandas",
                    description = "Aproveite as espreguiçadeiras ao ar livre. As mesas e cadeiras de plástico que estão na cozinha podem ser levadas livremente para o pátio para refeições ao ar livre."
                ),
                SectionItem(
                    title = "Rede de Descanso",
                    description = "Uma rede macia disponível para descanso e leitura na varanda."
                ),
                SectionItem(
                    title = "Piano",
                    description = "Disponível para tocar e relaxar com boas melodias durante a estadia."
                ),
                SectionItem(
                    title = "Guia da TV & Controles Remotos",
                    description = "A TV possui 2 controles remotos (um MAIOR e um MENOR). Siga o passo a passo para utilizar:",
                    steps = listOf(
                        "1. Com o controle remoto MAIOR, aperte o botão 'POWER' para ligar a TV. Em seguida, aperte o botão 'HOME'.",
                        "2. Na tela da TV, desça até a lista de entradas ('Entrada' ou 'Input').",
                        "3. Escolha HDMI 2 para utilizar a Sky Net (TV por assinatura) OU escolha HDMI 4 / ChromeCast para espelhar a tela do seu celular.",
                        "4. Caso tenha escolhido HDMI 2 (Sky Net), pegue agora o controle remoto MENOR: aperte 'MENU' e depois 'CANAIS' para escolher a programação desejada."
                    ),
                    badge = "Passo a Passo"
                )
            ),
            tips = listOf(
                "Para transmitir vídeos do YouTube ou Netflix do seu celular para a TV, selecione HDMI 4 (Chromecast) e conecte o celular no mesmo Wi-Fi ValeriaMR.",
                "Ao recolher-se à noite ou em caso de chuva, devolva as cadeiras de plástico e almofadas para a varanda coberta."
            )
        ),

        // (5) Cama, Banho & Conforto
        ManualSection(
            id = 5,
            title = "Cama, Banho & Conforto",
            subtitle = "Roupas extras, máquina de lavar e comodidades",
            shortSummary = "Enxoval completo, localização do secador e uso da máquina de lavar.",
            iconName = "Bed",
            accentColorHex = 0xFF007AFF,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/0c3ef2f5-1b19-48fc-9166-28c5564b0c11.jpeg",
            roomCategory = "Quartos & Banheiros",
            items = listOf(
                SectionItem(
                    title = "Roupas de Cama e Banho",
                    description = "São disponibilizadas roupas de cama, toalhas de banho e cobertas higienizadas para todos os hóspedes."
                ),
                SectionItem(
                    title = "Toalhas e Roupas Extras",
                    description = "No quarto de solteiro do andar de cima, dentro do armário branco, há toalhas e roupas de cama extras, além de colchas e cobertas adicionais para noites mais frias.",
                    badge = "Armário Branco Superior"
                ),
                SectionItem(
                    title = "Máquina de Lavar Roupas",
                    description = "Instrução obrigatória de uso:\n1. ANTES de escolher o programa de lavagem, gire o botão seletor para a posição 'DESLIGAR'.\n2. Só depois disso, selecione o programa e as configurações que desejar.\n3. O enchimento de água da máquina é bem lento, o que é perfeitamente normal.",
                    badge = "Atenção ao Girar"
                ),
                SectionItem(
                    title = "Secador de Cabelo & Itens de Banho",
                    description = "O secador de cabelo fica no rack da TV da sala, na prateleira do lado esquerdo.\nLá você também encontra sabonetes, shampoo, condicionador, papel higiênico e repelente."
                ),
                SectionItem(
                    title = "Ventiladores & Ferro de Passar",
                    description = "Os ventiladores ficam guardados no espaço embaixo da escada, caso necessário. Há também cabideiro com cabides, cortinas black-out, ferro de passar e varal para estender roupas."
                ),
                SectionItem(
                    title = "Chuveiros Elétricos",
                    description = "Água quentinha e abundante nos banheiros da casa."
                )
            ),
            tips = listOf(
                "As noites no sul de Minas podem ser frescas; aproveite as cobertas extras do armário do piso superior.",
                "Se precisar de repelente para o final da tarde no jardim, há frascos na prateleira esquerda do rack da TV."
            ),
            warnings = listOf(
                "FAVOR NÃO JOGAR PAPEL HIGIÊNICO NO VASO SANITÁRIO - utilize sempre a lixeira do banheiro para preservar o encanamento.",
                "Antes de ligar a máquina de lavar, sempre gire o botão para 'DESLIGAR' antes de colocar no ciclo desejado."
            )
        ),

        // (6) Cozinha Equipada
        ManualSection(
            id = 6,
            title = "Cozinha Equipada",
            subtitle = "Eletrodomésticos, café mineiro e alerta do forno",
            shortSummary = "Utensílios completos, café cortesia e atenção ao forno a gás.",
            iconName = "Restaurant",
            accentColorHex = 0xFFD9480F,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/901a4bc4-72b5-406a-94db-1a393b0a4b4c.jpeg",
            roomCategory = "Cozinha & Jantar",
            items = listOf(
                SectionItem(
                    title = "Eletrodomésticos Disponíveis",
                    description = "Refrigerador espaçoso, fogão com forno, Air Fryer, torradeira elétrica e liquidificador."
                ),
                SectionItem(
                    title = "Cantinho do Café Mineiro",
                    description = "Filtro coador de café tradicional, filtros de papel descartáveis e garrafa térmica para passar aquele café fresquinho."
                ),
                SectionItem(
                    title = "Despensa Básica de Cortesia",
                    description = "Disponibilizamos para o seu uso: pó de café, açúcar, adoçante, sal, vinagre e variedade de chás relaxantes."
                ),
                SectionItem(
                    title = "Utensílios & Mesa",
                    description = "Mesa de jantar ampla e mesas extras. Panelas, pirex de vidro, pratos, talheres, copos, taças de vinho e recipientes diversos."
                ),
                SectionItem(
                    title = "Atenção ao Forno a Gás",
                    description = "O forno a gás do fogão está atualmente com a chama baixa e pode apagar sozinho durante o uso. Fiquem atentos ao utilizá-lo. Recomendamos usar a Air Fryer para assar e dourar alimentos de forma mais rápida e prática!",
                    badge = "Alerta do Forno"
                )
            ),
            tips = listOf(
                "Aproveite a Air Fryer: além de prática, ela funciona perfeitamente como assadeira rápida para pães de queijo e lanches!",
                "Compre um queijo minas curado nas feirinhas da cidade para saborear com o café da tarde."
            ),
            warnings = listOf(
                "O forno a gás pode apagar se não supervisionado. Tenha cuidado ao acender.",
                "Evite apoiar panelas ferventes diretamente sobre a mesa de jantar de vidro temperado; utilize sempre os descansos de panela."
            )
        ),

        // (7) Regras, Segurança & Cuidados
        ManualSection(
            id = 7,
            title = "Regras & Segurança",
            subtitle = "Horário de silêncio, detectores e cuidados com a casa",
            shortSummary = "Silêncio das 22h às 7h, detectores de CO e cuidado com o degrau.",
            iconName = "Shield",
            accentColorHex = 0xFFC92A2A,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/05597ee6-5263-4d79-bd4d-b3f37d4f5103.jpeg",
            roomCategory = "Varanda & Corredor",
            items = listOf(
                SectionItem(
                    title = "Horário de Silêncio",
                    description = "Início às 22:00 e término às 07:00 da manhã. O bairro é residencial e muito calmo; pedimos a colaboração para preservar o descanso da vizinhança.",
                    badge = "22h às 07h"
                ),
                SectionItem(
                    title = "Fumo, Festas e Pets",
                    description = "• Fumar: É permitido fumar apenas na área externa/varanda da casa.\n• Pets: Não são permitidos (salvo com autorização prévia combinada com a anfitriã).\n• Eventos: Não são permitidos eventos ou confraternizações com som alto."
                ),
                SectionItem(
                    title = "Detectores de Monóxido de Carbono (Airbnb)",
                    description = "Por exigência e padrão de segurança do Airbnb, a casa possui dois detectores instalados:\n1. Um no corredor perto do banheiro do andar de baixo.\n2. Outro no corredor do segundo andar, bem em frente ao quarto maior.\n\nÀ noite, é normal ver uma luzinha verde bem discreta piscando no aparelho - isso indica apenas que ele está operando normalmente. Em qualquer risco real, um alarme sonoro soará.",
                    badge = "Segurança Ativa"
                ),
                SectionItem(
                    title = "Atenção ao Degrau Alto",
                    description = "Há um DEGRAU ALTO na varanda bem em frente à saída da cozinha. Tenha bastante cuidado ao circular por ali, especialmente no período noturno.",
                    badge = "Cuidado ao Pisar"
                ),
                SectionItem(
                    title = "Cuidados com a Estrutura da Casa",
                    description = "• Piso Laminado: Não deve ficar molhado nem ser limpo com produtos químicos abrasivos. Evite usar calçados com saltos pontiagudos sobre o laminado.\n• Mesa de Jantar: O tampo é de vidro temperado; evite contato térmico direto com panelas ou refratários muito quentes."
                )
            ),
            tips = listOf(
                "A luz verde piscando suavemente no corredor à noite é um sinal de que você e sua família estão protegidos pelos sensores de CO.",
                "Deixe uma luz da varanda acessa se for circular perto do degrau da cozinha à noite."
            ),
            warnings = listOf(
                "Cuidado com o degrau alto na saída da cozinha para a varanda!",
                "Não molhe o piso laminado nem use produtos químicos agressivos."
            )
        ),

        // (8) Lixo e Reciclagem
        ManualSection(
            id = 8,
            title = "Lixo & Reciclagem",
            subtitle = "Dias de coleta, cesto reciclável e lixeira externa",
            shortSummary = "Coleta às terças, quintas e sábados na lixeira suspensa.",
            iconName = "Delete",
            accentColorHex = 0xFF2B8A3E,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/5adc003b-d485-4e64-8b92-195c72a76605.jpeg",
            roomCategory = "Jardim & Calçada",
            items = listOf(
                SectionItem(
                    title = "Localização da Lixeira Externa",
                    description = "A lixeira da casa fica do lado de fora, suspensa na calçada, localizada atrás da árvore à esquerda do portão principal.",
                    badge = "Calçada / Árvore"
                ),
                SectionItem(
                    title = "Dias da Coleta do Caminhão",
                    description = "O caminhão de lixo público de São Lourenço passa nas:\n• Terças-feiras\n• Quintas-feiras\n• Sábados",
                    badge = "3ª, 5ª e Sábado"
                ),
                SectionItem(
                    title = "Separação de Recicláveis",
                    description = "Se vocês têm o ótimo hábito de separar o lixo reciclável (plásticos, latinhas, papéis secos e vidros), deixamos uma cesta exclusiva para isso ao lado da geladeira na cozinha."
                ),
                SectionItem(
                    title = "Higiene dos Banheiros",
                    description = "Lembrete gentil: Nunca jogue papel higiênico ou absorventes dentro do vaso sanitário. Utilize sempre o cesto de lixo do banheiro.",
                    badge = "Não Jogar no Vaso"
                )
            ),
            tips = listOf(
                "Coloque os sacos de lixo na lixeira suspensa na véspera dos dias de coleta para evitar acúmulo.",
                "A lixeira é suspensa exatamente para manter o lixo protegido de animais da rua."
            )
        ),

        // (9) Locomoção / Transporte / Garagem
        ManualSection(
            id = 9,
            title = "Transporte & Garagem",
            subtitle = "3 vagas, trânsito de São Lourenço e aplicativos",
            shortSummary = "3 vagas (1 coberta), apps G4 Mobile e UP, parada de ônibus.",
            iconName = "DirectionsCar",
            accentColorHex = 0xFF1098AD,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/4f2afea3-14c5-4e1d-80c1-ad49611cfabd.jpeg",
            roomCategory = "Garagem & Entrada",
            items = listOf(
                SectionItem(
                    title = "Vagas de Garagem",
                    description = "A casa conta com espaço para até 3 veículos na garagem interna privativa, sendo 1 vaga totalmente coberta.",
                    badge = "3 Vagas (1 Coberta)"
                ),
                SectionItem(
                    title = "Dica Importante sobre o Trânsito da Cidade",
                    description = "Atenção ao dirigir em São Lourenço: a cidade inteira possui apenas 4 semáforos instalados! Por tradição e respeito local, o pedestre tem prioridade absoluta em qualquer cruzamento, especialmente nas faixas de pedestre.",
                    badge = "Prioridade ao Pedestre"
                ),
                SectionItem(
                    title = "Aplicativos de Mobilidade Urbana",
                    description = "Em São Lourenço os aplicativos mais utilizados em vez do Uber são:\n• G4 Mobile (atendimento 24 horas)\n• UP Mobilidade Urbana\n• Rádio Táxis locais",
                    badge = "Apps Locais"
                ),
                SectionItem(
                    title = "Transporte Coletivo (Ônibus)",
                    description = "Bem em frente à Pousada Le Sapé há uma parada de ônibus circular urbano que passa com frequência regular de 1 em 1 hora."
                ),
                SectionItem(
                    title = "Rodoviária & Ônibus Intermunicipais",
                    description = "Principais companhias rodoviárias que atendem a cidade:\n• Rio de Janeiro <-> São Lourenço: Viação Útil / Sampaio\n• São Paulo <-> São Lourenço: Viação Cometa\n• Linhas diretas para Brasília e Caxambu / Circuito das Águas"
                )
            ),
            tips = listOf(
                "Sempre dê passagem com calma aos pedestres nas faixas; o ritmo de São Lourenço é tranquilo e acolhedor.",
                "G4 Mobile e UP funcionam muito bem e os motoristas conhecem cada cantinho da cidade."
            )
        ),

        // (10) Atividades e Entretenimento em São Lourenço
        ManualSection(
            id = 10,
            title = "Atividades & Lazer",
            subtitle = "Parque das Águas, balonismo, trem Maria Fumaça e passeios",
            shortSummary = "Parque das Águas, Quinta do Cedro, voo de balão e gastronomia.",
            iconName = "Explore",
            accentColorHex = 0xFFE67700,
            imageUrl = "https://a0.muscache.com/im/pictures/hosting/Hosting-1143502588915376302/original/cc1e572e-e523-44da-8e76-d6287b54f548.jpeg",
            roomCategory = "Pátio & Vista Panorâmica",
            items = listOf(
                SectionItem(
                    title = "Parque das Águas",
                    description = "O cartão-postal e coração de São Lourenço! São mais de 400 mil m² de área verde com 9 fontes de águas minerais naturais com propriedades medicinais carbogasosas, lago com pedalinhos, bosques floridos, pista de cooper e balneário com massagens e banhos relaxantes.",
                    badge = "Imperdível"
                ),
                SectionItem(
                    title = "Quinta do Cedro",
                    description = "Uma fazenda rural encantadora com restaurante típico mineiro servindo no tradicional fogão a lenha, animais de fazenda, degustação de cachaças, queijos artesanais e doces caseiros.",
                    badge = "Gastronomia & Família"
                ),
                SectionItem(
                    title = "Passeio de Balonismo",
                    description = "São Lourenço é conhecida como uma das capitais nacionais do balonismo! Os voos acontecem nas primeiras horas da manhã, proporcionando uma vista espetacular das montanhas da Mantiqueira ao nascer do sol.",
                    badge = "Aventura Inesquecível"
                ),
                SectionItem(
                    title = "Trem das Águas (Maria Fumaça)",
                    description = "Viagem nostálgica nos vagões históricos puxados por uma genuína locomotiva a vapor americana da década de 1920. O percurso vai até a cidade vizinha de Soledade de Minas, embalado por violeiros e degustação de queijos e doces.",
                    badge = "Passeio Histórico"
                ),
                SectionItem(
                    title = "Outros Atrativos da Região",
                    description = "• Praia de Minas (complexo de lazer aquático)\n• Fazendinha (passeio educativo com animais para crianças)\n• Voo Livre & Parapente no Mirante do Morro do Cruzeiro\n• Rota do Café Especial (Carmo de Minas / Fazendas centenárias)"
                )
            ),
            tips = listOf(
                "Leve uma garrafinha vazia para o Parque das Águas para provar cada uma das 9 fontes naturais (gasosa, alcalina, magnesiana, ferruginosa).",
                "Agende o voo de balão com antecedência, pois depende das condições climáticas do início da manhã."
            )
        )
    )

    val mapLocations: List<MapLocation> = listOf(
        MapLocation(
            id = "house",
            title = "Nossa Casa no Airbnb",
            category = LocationCategory.HOUSE,
            address = "Rua Pres. Castelo Branco, 95 - Ramon",
            description = "O seu refúgio aconchegante em São Lourenço. 3 vagas de garagem, varanda com espreguiçadeiras e jardim.",
            tip = "Lembre-se: puxe a porta antiga para fora enquanto gira a chave!",
            latitude = -22.1122,
            longitude = -45.0560,
            distanceEstimate = "0 metros (Você está aqui!)",
            tags = listOf("Acomodação", "Wi-Fi", "Garagem")
        ),
        MapLocation(
            id = "sape",
            title = "Pousada Le Sapé",
            category = LocationCategory.HOUSE,
            address = "Atrás da nossa rua (Ramon)",
            description = "Ponto de referência principal. A nossa casa fica na primeira rua exatamente atrás desta pousada.",
            tip = "Excelente referência para informar a motoristas de app e entregas.",
            latitude = -22.1125,
            longitude = -45.0553,
            distanceEstimate = "80 metros",
            tags = listOf("Referência", "Bairro Ramon")
        ),
        MapLocation(
            id = "bus_stop",
            title = "Parada de Ônibus Le Sapé",
            category = LocationCategory.TRANSPORT,
            address = "Em frente à Pousada Le Sapé",
            description = "Ponto de ônibus circular urbano que leva até o Centro de São Lourenço e Parque das Águas.",
            tip = "Passa com intervalo regular de 1 em 1 hora.",
            latitude = -22.1127,
            longitude = -45.0550,
            distanceEstimate = "110 metros",
            tags = listOf("Ônibus", "Circular")
        ),
        MapLocation(
            id = "trash_bin",
            title = "Lixeira Suspensa da Casa",
            category = LocationCategory.SERVICES,
            address = "Calçada da casa, à esquerda do portão",
            description = "Lixeira suspensa na calçada atrás da árvore. Coleta às 3as, 5as e sábados.",
            tip = "Cesto para recicláveis fica na cozinha ao lado da geladeira.",
            latitude = -22.1121,
            longitude = -45.0561,
            distanceEstimate = "15 metros (Na calçada)",
            tags = listOf("Coleta", "Reciclagem")
        ),
        MapLocation(
            id = "parque_aguas",
            title = "Parque das Águas de São Lourenço",
            category = LocationCategory.ATTRACTION,
            address = "Praça João Lage, Centro",
            description = "Principal ponto turístico da cidade com 9 fontes de água mineral com propriedades terapêuticas e balneário.",
            tip = "Entrada aberta das 8h às 17h. O balneário oferece banhos de imersão e massagens.",
            latitude = -22.1189,
            longitude = -45.0515,
            distanceEstimate = "1,8 km (~5 min de carro)",
            tags = listOf("Águas Termais", "Lago", "Imperdível")
        ),
        MapLocation(
            id = "quinta_cedro",
            title = "Quinta do Cedro",
            category = LocationCategory.FOOD,
            address = "Estrada São Lourenço / Carmo de Minas",
            description = "Fazenda típica com almoço mineiro no fogão a lenha, queijaria artesanal e animais para as crianças.",
            tip = "Chegue por volta das 12h nos fins de semana para aproveitar o buffet sem fila.",
            latitude = -22.1030,
            longitude = -45.0680,
            distanceEstimate = "2,9 km (~7 min de carro)",
            tags = listOf("Comida Mineira", "Fazenda", "Doces")
        ),
        MapLocation(
            id = "trem_aguas",
            title = "Estação do Trem das Águas (Maria Fumaça)",
            category = LocationCategory.ATTRACTION,
            address = "Praça Dr. Humberto Sanches, Centro",
            description = "Estação de partida do trem a vapor histórico que faz o trajeto até Soledade de Minas.",
            tip = "Passeios aos sábados e domingos com música caipira e degustação.",
            latitude = -22.1165,
            longitude = -45.0488,
            distanceEstimate = "2,1 km (~6 min de carro)",
            tags = listOf("História", "Maria Fumaça", "Passeio")
        ),
        MapLocation(
            id = "balonismo",
            title = "Ponto de Decolagem de Balões",
            category = LocationCategory.ATTRACTION,
            address = "Campos de decolagem de São Lourenço",
            description = "Voos livres de balão de ar quente ao nascer do sol pela Serra da Mantiqueira.",
            tip = "Os passeios saem bem cedinho (5h30 da manhã) para aproveitar ventos suaves.",
            latitude = -22.1250,
            longitude = -45.0430,
            distanceEstimate = "3,5 km (~9 min)",
            tags = listOf("Balão", "Nascer do Sol", "Aventura")
        ),
        MapLocation(
            id = "morro_cruzeiro",
            title = "Mirante do Morro do Cruzeiro & Parapente",
            category = LocationCategory.ATTRACTION,
            address = "Acesso via teleférico ou carro",
            description = "Vista panorâmica 360° da cidade e pista de salto para voo livre de parapente.",
            tip = "Pôr do sol cinematográfico com vista para o vale.",
            latitude = -22.1280,
            longitude = -45.0590,
            distanceEstimate = "3,2 km (~10 min)",
            tags = listOf("Mirante", "Parapente", "Pôr do Sol")
        ),
        MapLocation(
            id = "rodoviaria",
            title = "Terminal Rodoviário de São Lourenço",
            category = LocationCategory.TRANSPORT,
            address = "Av. Damião Junqueira de Souza, 900",
            description = "Partida e chegada dos ônibus da Cometa (SP), Útil/Sampaio (RJ) e intermunicipais.",
            tip = "Conta com praça de alimentação e guichê de táxi 24 horas.",
            latitude = -22.1215,
            longitude = -45.0420,
            distanceEstimate = "2,7 km (~7 min)",
            tags = listOf("Rodoviária", "Cometa", "Útil")
        )
    )

    val floorPlanPoints: List<FloorPlanPoint> = listOf(
        // 1. Entrada Principal
        FloorPlanPoint(
            id = "entrada_principal",
            title = "Entrada Principal",
            category = FloorPlanCategory.ENTRANCE_ACCESS,
            roomOrZone = "Fachada Frontal / Sala",
            description = "Porta de entrada principal charmosa e de estilo colonial. Segredo da fechadura: para destrancar e abrir com facilidade, puxe suavemente a folha da porta para fora com uma mão enquanto gira a chave no tambor.",
            tipOrWarning = "Não force a chave: puxe a porta para fora suavemente com a outra mão ao girar a chave.",
            isWarning = false,
            badge = "Segredo da Chave",
            normX = 0.42f,
            normY = 0.68f,
            iconName = "Key",
            manualSectionId = 3,
            tags = listOf("Chaves", "Entrada", "Check-in")
        ),
        // 2. Porta dos Fundos
        FloorPlanPoint(
            id = "porta_fundos",
            title = "Porta dos Fundos",
            category = FloorPlanCategory.ENTRANCE_ACCESS,
            roomOrZone = "Área de Serviço / Pátio",
            description = "Porta que conecta a cozinha e a área de serviço ao pátio ensolarado e às varandas externas. Chave identificada no chaveiro da casa.",
            tipOrWarning = "Mantenha trancada ao sair para passeios.",
            isWarning = false,
            badge = "Acesso ao Pátio",
            normX = 0.78f,
            normY = 0.32f,
            iconName = "DoorBack",
            manualSectionId = 3,
            tags = listOf("Fundos", "Acesso", "Pátio")
        ),
        // 3. Portão de Pedestres
        FloorPlanPoint(
            id = "portao_pedestres",
            title = "Portão de Pedestres",
            category = FloorPlanCategory.ENTRANCE_ACCESS,
            roomOrZone = "Muro Frontal / Calçada",
            description = "Portãozinho de pedestres para entrada e saída a pé. Trancado com a chave menor do chaveiro. Dá acesso direto à calçada e à Rua Pres. Castelo Branco.",
            tipOrWarning = "Abra com a chave pequena do chaveiro.",
            isWarning = false,
            badge = "Acesso a Pé",
            normX = 0.38f,
            normY = 0.88f,
            iconName = "DirectionsWalk",
            manualSectionId = 3,
            tags = listOf("Portão", "Calçada", "Acesso")
        ),
        // 4. Pátio
        FloorPlanPoint(
            id = "patio",
            title = "Pátio Externo",
            category = FloorPlanCategory.PARKING_OUTDOORS,
            roomOrZone = "Área Externa dos Fundos",
            description = "Espaço ao ar livre ensolarado e privativo com piso antiderrapante e espreguiçadeiras. As mesas e cadeiras plásticas da cozinha podem ser levadas para cá para refeições ao ar livre. É também a única área permitida para fumantes (fumo proibido no interior da casa).",
            tipOrWarning = "Área aberta ideal para descanso ao sol e única área onde é permitido fumar.",
            isWarning = false,
            badge = "Sol & Relax",
            normX = 0.74f,
            normY = 0.18f,
            iconName = "Yard",
            manualSectionId = 4,
            tags = listOf("Pátio", "Espreguiçadeiras", "Ar Livre", "Fumo")
        ),
        // 5. Varandas
        FloorPlanPoint(
            id = "varandas",
            title = "Varandas da Casa",
            category = FloorPlanCategory.LIVING_LEISURE,
            roomOrZone = "Varandas Cobertas",
            description = "Varanda coberta e arejada contornando a lateral da casa com vista para o jardim e o clima serrano. Contém a rede de descanso e espreguiçadeiras.",
            tipOrWarning = "Atenção ao degrau alto na transição da varanda para a cozinha/sala! Cuidado ao caminhar, principalmente à noite.",
            isWarning = true,
            badge = "Atenção ao Degrau",
            normX = 0.28f,
            normY = 0.40f,
            iconName = "Deck",
            manualSectionId = 7,
            tags = listOf("Varanda", "Degrau", "Jardim", "Vista")
        ),
        // 6. Rede
        FloorPlanPoint(
            id = "rede",
            title = "Rede de Descanso",
            category = FloorPlanCategory.LIVING_LEISURE,
            roomOrZone = "Varanda Coberta",
            description = "Rede de tecido macio e higienizada, instalada nos ganchos reforçados da varanda coberta. Excelente para tirar uma soneca, ler um livro ou sentir a brisa fresca de São Lourenço.",
            tipOrWarning = "Desfrute do canto dos pássaros e da brisa suave da Mantiqueira.",
            isWarning = false,
            badge = "Descanso & Leitura",
            normX = 0.20f,
            normY = 0.38f,
            iconName = "AirlineSeatFlat",
            manualSectionId = 4,
            tags = listOf("Rede", "Relaxar", "Soneca", "Varanda")
        ),
        // 7. Piano
        FloorPlanPoint(
            id = "piano",
            title = "Piano Acústico",
            category = FloorPlanCategory.LIVING_LEISURE,
            roomOrZone = "Sala de Estar",
            description = "Piano acústico vertical de parede na sala de estar. Disponível para os hóspedes tocarem melodias suaves e apreciarem música durante a estadia. Pedimos cuidado com o instrumento e respeito estrito ao horário de silêncio (22h às 07h).",
            tipOrWarning = "Horário de silêncio: 22:00 às 07:00 da manhã.",
            isWarning = false,
            badge = "Instrumento Musical",
            normX = 0.60f,
            normY = 0.58f,
            iconName = "Piano",
            manualSectionId = 4,
            tags = listOf("Piano", "Música", "Sala de Estar")
        ),
        // 8. Localização da TV e seus controles
        FloorPlanPoint(
            id = "tv_controles",
            title = "TV e seus Controles Remotos",
            category = FloorPlanCategory.LIVING_LEISURE,
            roomOrZone = "Sala de Estar (Rack)",
            description = "Smart TV montada na sala com 2 controles remotos no rack:\n• Controle MAIOR: Aperte 'POWER' para ligar e depois 'HOME'. Na tela, selecione HDMI 2 (Sky Net) ou HDMI 4 (Chromecast/espelhar celular).\n• Controle MENOR: Caso use HDMI 2, aperte 'MENU' e 'CANAIS' para escolher o que assistir na Sky.",
            tipOrWarning = "Controle maior liga e escolhe HDMI (2 para Sky, 4 para Chromecast); controle menor troca canais da Sky.",
            isWarning = false,
            badge = "2 Controles Remotos",
            normX = 0.42f,
            normY = 0.58f,
            iconName = "Tv",
            manualSectionId = 4,
            tags = listOf("TV", "Controles", "Sky Net", "Chromecast")
        ),
        // 9. Localização dos ventiladores
        FloorPlanPoint(
            id = "ventiladores",
            title = "Ventiladores Portáteis",
            category = FloorPlanCategory.COMFORT_SERVICE,
            roomOrZone = "Quartos & Sala",
            description = "Ventiladores portáteis e silenciosos disponíveis nos quartos e na sala para manter o ar em circulação nos dias mais quentes. Voltagem da casa: 110V.",
            tipOrWarning = "Podem ser posicionados onde for mais conveniente nos cômodos.",
            isWarning = false,
            badge = "110V Silenciosos",
            normX = 0.25f,
            normY = 0.55f,
            iconName = "ModeFan",
            manualSectionId = 5,
            tags = listOf("Ventilador", "Climatização", "Quartos")
        ),
        // 10. Secador de cabelo
        FloorPlanPoint(
            id = "secador_cabelo",
            title = "Secador de Cabelo",
            category = FloorPlanCategory.COMFORT_SERVICE,
            roomOrZone = "Rack da TV (Sala)",
            description = "Secador de cabelo 110V com controle de ar quente e frio. Fica guardado dentro da gaveta/porta inferior do móvel rack da televisão na sala de estar.",
            tipOrWarning = "Localização exata: gaveta inferior do rack da TV na sala.",
            isWarning = false,
            badge = "No Rack da TV",
            normX = 0.50f,
            normY = 0.58f,
            iconName = "Air",
            manualSectionId = 5,
            tags = listOf("Secador", "Cabelo", "Rack da TV", "Banheiro")
        ),
        // 11. Máquina de lavar
        FloorPlanPoint(
            id = "maquina_lavar",
            title = "Máquina de Lavar Roupas",
            category = FloorPlanCategory.COMFORT_SERVICE,
            roomOrZone = "Área de Serviço / Lavanderia",
            description = "Lavadora automática na área de serviço externa coberta.\nINSTRUÇÃO OBRIGATÓRIA: Antes de girar o botão para escolher o programa de lavagem, coloque o botão primeiro na posição 'DESLIGAR'. Em seguida, selecione o programa desejado.",
            tipOrWarning = "Gire o botão para DESLIGAR antes de escolher o ciclo. O enchimento de água é lento por padrão da máquina.",
            isWarning = true,
            badge = "Aviso: Girar para DESLIGAR",
            normX = 0.84f,
            normY = 0.36f,
            iconName = "LocalLaundryService",
            manualSectionId = 5,
            tags = listOf("Lavanderia", "Máquina de Lavar", "Roupas")
        ),
        // 12. Filtro de café
        FloorPlanPoint(
            id = "filtro_cafe",
            title = "Filtro de Café & Garrafa",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Bancada da Cozinha",
            description = "Kit café com coador/filtro, suporte e garrafa térmica na bancada da cozinha. Disponibilizamos café mineiro e açúcar como cortesia para você preparar café passado na hora.",
            tipOrWarning = "Café e açúcar de cortesia disponíveis na bancada.",
            isWarning = false,
            badge = "Café Mineiro",
            normX = 0.68f,
            normY = 0.48f,
            iconName = "Coffee",
            manualSectionId = 6,
            tags = listOf("Café", "Filtro", "Garrafa Térmica", "Cozinha")
        ),
        // 13. Geladeira
        FloorPlanPoint(
            id = "geladeira",
            title = "Geladeira Duplex",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Cozinha (Canto Esquerdo)",
            description = "Geladeira duplex ampla com congelador separado e formas de gelo. Ao lado da geladeira fica a lixeira/cesto específico para materiais recicláveis secos.",
            tipOrWarning = "Cesto de recicláveis (plástico, papel, latinhas) fica ao lado da geladeira.",
            isWarning = false,
            badge = "Duplex com Freezer",
            normX = 0.64f,
            normY = 0.42f,
            iconName = "Kitchen",
            manualSectionId = 6,
            tags = listOf("Geladeira", "Freezer", "Cozinha", "Recicláveis")
        ),
        // 13.5 Cozinha Equipada & Bancada
        FloorPlanPoint(
            id = "cozinha",
            title = "Cozinha Equipada",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Cozinha & Espaço Gourmet",
            description = "Cozinha completa com fogão 4 bocas, geladeira duplex, kit café mineiro com garrafa térmica, torradeira e Air Fryer prática na bancada. Mesas e cadeiras podem ser levadas para o pátio.",
            tipOrWarning = "Atenção de segurança: Não use o forno a gás se puder optar pela Air Fryer! Muito mais prática e segura.",
            isWarning = true,
            badge = "Atenção: Forno a Gás",
            normX = 0.72f,
            normY = 0.44f,
            iconName = "Kitchen",
            manualSectionId = 6,
            tags = listOf("Cozinha", "Fogão", "Air Fryer", "Forno", "Café")
        ),
        // 14. Fogão
        FloorPlanPoint(
            id = "fogao",
            title = "Fogão 4 Bocas",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Bancada da Cozinha",
            description = "Fogão a gás de 4 bocas com acendimento para preparar suas refeições com praticidade. Panelas, frigideiras e utensílios completos estão nos armários.",
            tipOrWarning = "Mantenha o registro de gás seguro após o uso.",
            isWarning = false,
            badge = "4 Bocas",
            normX = 0.72f,
            normY = 0.42f,
            iconName = "SoupKitchen",
            manualSectionId = 6,
            tags = listOf("Fogão", "Cozinha", "Gás")
        ),
        // 15. Forno
        FloorPlanPoint(
            id = "forno",
            title = "Forno a Gás (Alerta de Segurança)",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Cozinha (Sob o Fogão)",
            description = "Forno a gás tradicional. ATENÇÃO DE SEGURANÇA: A chama do forno a gás é baixa e requer muito cuidado e vigilância no acendimento manual. Recomendamos enfaticamente que utilizem a Air Fryer para assados, pois é muito mais prática, rápida e segura!",
            tipOrWarning = "Atenção: Chama baixa requer cautela manual. Dê preferência à Air Fryer para assados!",
            isWarning = true,
            badge = "Recomendamos a Air Fryer",
            normX = 0.72f,
            normY = 0.45f,
            iconName = "Warning",
            manualSectionId = 6,
            tags = listOf("Forno", "Gás", "Alerta", "Cozinha")
        ),
        // 16. Air Fryer
        FloorPlanPoint(
            id = "air_fryer",
            title = "Air Fryer (Fritadeira sem Óleo)",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Bancada da Cozinha",
            description = "Fritadeira elétrica sem óleo super prática. Perfeita para assar pães de queijo mineiros quentinhos, batatas, legumes e lanches rápidos de maneira limpa e sem fumaça.",
            tipOrWarning = "Nossa recomendação oficial para evitar usar o forno a gás.",
            isWarning = false,
            badge = "Prática & Segura",
            normX = 0.76f,
            normY = 0.46f,
            iconName = "DinnerDining",
            manualSectionId = 6,
            tags = listOf("Air Fryer", "Cozinha", "Pão de Queijo")
        ),
        // 17. Torradeira
        FloorPlanPoint(
            id = "torradeira",
            title = "Torradeira Elétrica",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Bancada da Cozinha",
            description = "Torradeira elétrica rápida para tostar pães de forma, ciabattas e torradas crocantes para o café da manhã.",
            tipOrWarning = "Regule a intensidade da tostagem no seletor lateral.",
            isWarning = false,
            badge = "Café da Manhã",
            normX = 0.80f,
            normY = 0.44f,
            iconName = "BakeryDining",
            manualSectionId = 6,
            tags = listOf("Torradeira", "Pão", "Café da Manhã", "Cozinha")
        ),
        // 18. Liquidificador
        FloorPlanPoint(
            id = "liquidificador",
            title = "Liquidificador",
            category = FloorPlanCategory.KITCHEN_APPLIANCES,
            roomOrZone = "Bancada / Armário da Cozinha",
            description = "Liquidificador com copo dosador e lâminas afiadas para preparar sucos naturais de frutas, vitaminas, molhos e massas leves.",
            tipOrWarning = "Voltagem 110V.",
            isWarning = false,
            badge = "Sucos & Vitaminas",
            normX = 0.64f,
            normY = 0.48f,
            iconName = "Blender",
            manualSectionId = 6,
            tags = listOf("Liquidificador", "Sucos", "Cozinha")
        ),
        // 19. Mesa de jantar
        FloorPlanPoint(
            id = "mesa_jantar",
            title = "Mesa de Jantar de Vidro",
            category = FloorPlanCategory.LIVING_LEISURE,
            roomOrZone = "Sala de Jantar Integrada",
            description = "Mesa de jantar ampla com tampo de vidro temperado e cadeiras confortáveis.\nAVISO DE CUIDADO: Utilize sempre jogos americanos e apoios térmicos de prato. NUNCA coloque panelas, travessas ou refratários quentes diretamente sobre o vidro temperado para evitar choque térmico.",
            tipOrWarning = "Cuidado: Nunca apoie panelas ou recipientes quentes diretamente sobre o tampo de vidro!",
            isWarning = true,
            badge = "Vidro Temperado",
            normX = 0.52f,
            normY = 0.48f,
            iconName = "TableRestaurant",
            manualSectionId = 7,
            tags = listOf("Mesa", "Jantar", "Vidro", "Cuidado")
        ),
        // 20. Garagem (com indicação de vagas cobertas e descobertas)
        FloorPlanPoint(
            id = "garagem",
            title = "Garagem (1 Coberta + 2 Descobertas)",
            category = FloorPlanCategory.PARKING_OUTDOORS,
            roomOrZone = "Pátio Frontal de Estacionamento",
            description = "Área de estacionamento privativa com capacidade total para 3 carros:\n• 1 Vaga Coberta sob a laje/alpendre (em frente à porta)\n• 2 Vagas Descobertas no pátio pavimentado\nPortão de veículos acionado eletronicamente pelo botão superior direito do controle remoto.",
            tipOrWarning = "Abra e feche pelo botão superior direito do controle. A chave preta do chaveiro é reserva de emergência caso falte luz.",
            isWarning = false,
            badge = "3 Vagas (1 Coberta + 2 Descobertas)",
            normX = 0.22f,
            normY = 0.78f,
            iconName = "DirectionsCar",
            manualSectionId = 3,
            tags = listOf("Garagem", "Vagas Cobertas", "Vagas Descobertas", "Portão Eletrônico")
        ),
        // 21. Lixeira externa
        FloorPlanPoint(
            id = "lixeira_externa",
            title = "Lixeira Externa na Calçada",
            category = FloorPlanCategory.SURROUNDINGS,
            roomOrZone = "Calçada (À esquerda do portão)",
            description = "Lixeira suspensa de ferro localizada na calçada, exatamente atrás da árvore à esquerda do portão da casa. Dias de coleta do caminhão de lixo: Terças, Quintas e Sábados pelo período da manhã.",
            tipOrWarning = "Descarte o lixo doméstico devidamente ensacado na lixeira suspensa.",
            isWarning = false,
            badge = "Coleta: Ter, Qui e Sáb",
            normX = 0.16f,
            normY = 0.92f,
            iconName = "Delete",
            manualSectionId = 9,
            tags = listOf("Lixeira", "Coleta", "Calçada", "Reciclagem")
        ),
        // 22. Parada de ônibus próxima
        FloorPlanPoint(
            id = "parada_onibus",
            title = "Parada de Ônibus Próxima",
            category = FloorPlanCategory.SURROUNDINGS,
            roomOrZone = "Em frente à Pousada Le Sapé",
            description = "Ponto de ônibus circular urbano localizado a apenas 110 metros da casa, bem em frente à Pousada Le Sapé. Passa a cada 1 hora e liga o bairro Ramon ao Centro, Parque das Águas e Rodoviária.",
            tipOrWarning = "Passa de 1 em 1 hora. Tarifa urbana municipal acessível.",
            isWarning = false,
            badge = "Circular (1 em 1h)",
            normX = 0.88f,
            normY = 0.88f,
            iconName = "DirectionsBus",
            manualSectionId = 9,
            tags = listOf("Ônibus", "Transporte", "Circular", "Centro")
        ),
        // 23. Pousada Le Sapé
        FloorPlanPoint(
            id = "pousada_le_sape",
            title = "Pousada Le Sapé (Ponto de Referência)",
            category = FloorPlanCategory.SURROUNDINGS,
            roomOrZone = "Rua Imediata à Frente / Ramon",
            description = "Pousada tradicional e principal ponto de referência geográfica do bairro Ramon. Nossa casa fica localizada na primeira rua exatamente atrás do Le Sapé. É a melhor referência para indicar em entregas de comida e corridas de aplicativo (G4 Mobile/UP).",
            tipOrWarning = "Dica para táxi ou delivery: 'Primeira rua logo atrás da Pousada Le Sapé'.",
            isWarning = false,
            badge = "Ponto de Referência",
            normX = 0.75f,
            normY = 0.90f,
            iconName = "Hotel",
            manualSectionId = 2,
            tags = listOf("Le Sapé", "Referência", "Ramon", "Pousada")
        ),
        // 24. Rua Presidente Castelo Branco 95 Ramon
        FloorPlanPoint(
            id = "rua_castelo_branco",
            title = "Rua Pres. Castelo Branco, 95 - Ramon",
            category = FloorPlanCategory.SURROUNDINGS,
            roomOrZone = "Endereço Oficial da Residência",
            description = "Rua residencial calma, arborizada e segura no bairro Ramon, São Lourenço - MG (CEP 37470-000). A cidade inteira conta com apenas 4 semáforos, e o pedestre tem prioridade absoluta na faixa.",
            tipOrWarning = "Clique em 'Copiar Endereço' ou 'Navegar no Maps' para abrir a rota no Waze ou Google Maps.",
            isWarning = false,
            badge = "Endereço Oficial",
            normX = 0.45f,
            normY = 0.95f,
            iconName = "Place",
            manualSectionId = 2,
            tags = listOf("Endereço", "Rua", "Castelo Branco", "Localização")
        )
    )


    val localRecommendations: List<LocalRecommendation> = listOf(
        LocalRecommendation(
            id = "rec_1",
            title = "Parque das Águas & Balneário Termal",
            category = "Passeios Clássicos",
            description = "O parque mais famoso do Circuito das Águas. Possui 9 fontes minerais com propriedades comprovadas para saúde digestiva e renal.",
            hostTip = "Não deixe de fazer uma massagem relaxante ou banho de imersão de águas carbogasosas no Balneário Dr. Saturnino!",
            address = "Praça João Lage, Centro",
            priceLevel = "R$ 16 a R$ 20 (Entrada)",
            isMustVisit = true,
            tags = listOf("Águas Termais", "Pedalinho", "Família", "Natureza")
        ),
        LocalRecommendation(
            id = "rec_2",
            title = "Restaurante da Quinta do Cedro",
            category = "Gastronomia Mineira",
            description = "O autêntico banquete da roça no fogão a lenha: costelinha suína, tutu à mineira, leitoa pururuca, couve fininha e frango caipira.",
            hostTip = "Após o almoço, visite a doceria da fazenda e experimente o doce de leite puro em tachos de cobre.",
            address = "Estrada Rural de São Lourenço",
            priceLevel = "$$$ (Buffet livre)",
            isMustVisit = true,
            tags = listOf("Comida Mineira", "Fogão a Lenha", "Doces Caseiros")
        ),
        LocalRecommendation(
            id = "rec_3",
            title = "Voo de Balão sobre a Mantiqueira",
            category = "Aventura & Vista",
            description = "São Lourenço possui uma das melhores geografias do mundo para o voo de balões. A vista aérea dos cafezais e montanhas é de tirar o fôlego.",
            hostTip = "Reserve com pelo menos 2 dias de antecedência. O voo é calmo, seguro e finaliza com brinde de espumante!",
            address = "Decolagem no campo da cidade",
            priceLevel = "$$$$ (Experiência Premium)",
            isMustVisit = true,
            tags = listOf("Balonismo", "Romântico", "Fotografia")
        ),
        LocalRecommendation(
            id = "rec_4",
            title = "Trem das Águas (Maria Fumaça)",
            category = "Passeios Clássicos",
            description = "Viagem histórica de 10 km até Soledade de Minas em vagões preservados dos anos 1920.",
            hostTip = "O passeio é muito animado com músicos tocando clássicos caipiras nos vagões!",
            address = "Estação Ferroviária Central",
            priceLevel = "$$ (Ingresso com degustação)",
            isMustVisit = true,
            tags = listOf("História", "Crianças", "Música")
        ),
        LocalRecommendation(
            id = "rec_5",
            title = "Rota do Café Especial (Unique Cafés)",
            category = "Cafés & Degustação",
            description = "A região é premiada mundialmente pela produção dos melhores grãos arábica especiais do Brasil. Cafeteria com métodos manuais (V60, Chemex, Aeropress).",
            hostTip = "Peça o café coado na hora acompanhado de uma broa de milho com queijo canastra derretido.",
            address = "Próximo à entrada da cidade",
            priceLevel = "$$",
            isMustVisit = false,
            tags = listOf("Café Gourmet", "Cafeteria", "Lembranças")
        ),
        LocalRecommendation(
            id = "rec_6",
            title = "Praia de Minas",
            category = "Lazer & Relaxamento",
            description = "Complexo de lazer ecológico com piscina natural, pedalinho, tirolesa e restaurante à beira do lago.",
            hostTip = "Ótima opção para um dia ensolarado com crianças pequenas.",
            address = "Zona Rural próxima",
            priceLevel = "$$",
            isMustVisit = false,
            tags = listOf("Piscina", "Lago", "Família")
        ),
        LocalRecommendation(
            id = "rec_7",
            title = "Fazendinha & Trilha Ecológica",
            category = "Passeios Clássicos",
            description = "Espaço rural dedicado à vivência infantil com pôneis, coelhos, cabritos e ordenha de vacas.",
            hostTip = "Excelente passeio para a criançada entrar em contato direto com a natureza.",
            address = "Estrada para Soledade",
            priceLevel = "$",
            isMustVisit = false,
            tags = listOf("Animais", "Crianças", "Rural")
        ),
        LocalRecommendation(
            id = "rec_8",
            title = "Mirante do Morro do Cruzeiro",
            category = "Aventura & Vista",
            description = "O ponto mais alto da cidade com vista panorâmica. Pode-se subir de carro ou pelo teleférico que parte do centro.",
            hostTip = "Vá no fim da tarde para acompanhar os saltos de parapente com a luz dourada do entardecer.",
            address = "Morro do Cruzeiro",
            priceLevel = "Gratuito (ou teleférico pago)",
            isMustVisit = false,
            tags = listOf("Pôr do Sol", "Teleférico", "Mirante")
        )
    )

    val quickQuestions = listOf(
        "Falar com a dona no WhatsApp",
        "Qual a senha do Wi-Fi?",
        "Como abre a porta antiga?",
        "Como ligar a TV e canais?",
        "Onde fica o lixo da casa?",
        "Como usar a máquina de lavar?",
        "Onde tem toalhas extras?",
        "Quais as regras de silêncio?",
        "Como funciona o portão de carros?",
        "O forno a gás tem algum detalhe?",
        "Quais apps de transporte usar?"
    )

    // Offline smart answers for instant zero-latency responses
    fun getInstantAnswer(question: String): String? {
        val q = question.lowercase().trim()
        return when {
            q.contains("whatsapp") || q.contains("zap") || q.contains("dona") || q.contains("proprietária") || q.contains("valéria") || q.contains("anfitriã") -> {
                "💬 **WhatsApp Direto da Proprietária:**\n• **Anfitriã:** Valéria\n• **Telefone / WhatsApp:** $HOST_PHONE_DISPLAY\n\nVocê pode tocar no botão verde acima ou no botão do WhatsApp ao lado da caixa de mensagem para enviar sua dúvida diretamente para o celular dela!"
            }
            q.contains("wifi") || q.contains("wi-fi") || q.contains("internet") || q.contains("senha") -> {
                "📶 **Wi-Fi da Casa:**\n• **Rede:** $WIFI_SSID\n• **Senha:** $WIFI_PASSWORD\n\nVocê também pode copiar a senha com 1 toque no cartão de Wi-Fi da tela inicial!"
            }
            q.contains("porta antiga") || (q.contains("porta") && q.contains("abrir")) -> {
                "🚪 **Dica de Ouro para a Porta de Entrada:**\nÉ uma porta antiga de madeira! Para abri-la facilmente, **puxe a folha da porta para fora com a outra mão enquanto gira a chave**. Assim a lingueta da fechadura desliza suavemente sem travar."
            }
            q.contains("tv") || q.contains("televis") || q.contains("controle") || q.contains("skynet") || q.contains("sky") -> {
                "📺 **Passo a Passo da TV (2 Controles):**\n1. **Controle MAIOR:** Aperte 'POWER' para ligar e depois 'HOME'.\n2. Na tela, vá em 'Entrada/Input' e escolha:\n   - **HDMI 2** para Sky Net\n   - **HDMI 4 / Chromecast** para espelhar celular\n3. Se escolheu HDMI 2, use o **controle MENOR**: aperte 'MENU' e depois 'CANAIS' para escolher o canal desejado."
            }
            q.contains("lixo") || q.contains("recicl") -> {
                "🗑️ **Lixo e Reciclagem:**\n• **Lixeira Externa:** Fica suspensa na calçada, atrás da árvore à esquerda do portão.\n• **Coleta do Caminhão:** Às 3ªs, 5ªs e sábados.\n• **Recicláveis:** Há uma cesta ao lado da geladeira.\n• **Banheiro:** Nunca jogue papel higiênico no vaso sanitário!"
            }
            q.contains("máquina") || q.contains("lavar") || q.contains("roupa") -> {
                "🧺 **Máquina de Lavar Roupas:**\n• **Passo Obrigatório:** Antes de escolher o programa de lavagem, gire o botão para a posição **'DESLIGAR'**.\n• Só depois disso selecione o ciclo desejado.\n• **Obs:** O enchimento de água da máquina é bem lento, é o funcionamento normal dela!"
            }
            q.contains("toalha") || q.contains("coberta") || q.contains("lençol") || q.contains("cama") -> {
                "🛏️ **Roupas de Cama e Toalhas Extras:**\nFicam guardadas no **armário branco do quarto de solteiro do andar de cima**! Lá você encontra toalhas extras, roupas de cama, colchas e cobertas bem quentinhas."
            }
            q.contains("silêncio") || q.contains("som") || q.contains("festa") || q.contains("regra") -> {
                "🤫 **Regras da Casa:**\n• **Horário de Silêncio:** 22:00 às 07:00 da manhã.\n• **Fumar:** Permitido apenas nas áreas abertas/varanda.\n• **Pets:** Não permitidos sem autorização prévia.\n• **Eventos:** Não são permitidos eventos com som alto."
            }
            q.contains("portão") || q.contains("garagem") || q.contains("carro") || q.contains("estacionamento") -> {
                "🚗 **Garagem e Portão Eletrônico:**\n• A casa possui 3 vagas (1 coberta).\n• Para abrir o portão de carros, aperte o **botão superior direito do controle remoto**.\n• A chave preta do chaveiro é reserva caso falte energia."
            }
            q.contains("forno") || q.contains("fogão") || q.contains("cozinha") || q.contains("chama") -> {
                "🍳 **Dica da Cozinha & Forno:**\nO forno a gás está com a chama baixa e pode apagar sozinho. Fiquem bem atentos ao utilizá-lo! Recomendamos usar a **Air Fryer** que assa com rapidez e segurança."
            }
            q.contains("uber") || q.contains("transporte") || q.contains("táxi") || q.contains("g4") || q.contains("gw") || q.contains("ônibus") -> {
                "🚕 **Mobilidade em São Lourenço:**\n• Aplicativos locais: **G4 Mobile (24h)** e **UP Mobilidade Urbana**.\n• Ônibus: Parada bem em frente à Pousada Le Sapé (passa de 1 em 1 hora).\n• Atenção ao trânsito: A cidade tem apenas 4 semáforos, o pedestre sempre tem a preferência na faixa!"
            }
            q.contains("degrau") || q.contains("cuidado") || q.contains("seguran") || q.contains("monoxido") || q.contains("alarme") -> {
                "⚠️ **Segurança & Cuidados:**\n• **Degrau alto:** Na varanda bem em frente à cozinha. Cuidado redobrado à noite!\n• **Detectores de CO:** No corredor térreo e no 2º andar. A luzinha verde piscando indica funcionamento normal.\n• **Piso laminado:** Não molhar nem usar produtos químicos agressivos."
            }
            else -> null
        }
    }

    // Cômodos detalhados e distribuição da acomodação (Anúncio Airbnb #1143502588915376302)
    val houseRooms: List<RoomItem> = listOf(
        RoomItem(
            id = "room_facade",
            title = "Fachada & Jardins Frontais",
            floor = "Área Externa & Entrada",
            subtitle = "Entrada imponente, rampa de veículos, escadaria e jardins",
            bedConfig = "2 pavimentos • Jardins com palmeiras • Bairro Ramon",
            description = "Arquitetura charmosa com escadaria revestida em pedra São Tomé, rampa lateral calçada para veículos, palmeiras imperiais, varanda frontal e jardins bem cuidados.",
            amenities = listOf("Vista para a serra", "Portão automático", "Escadaria de pedras", "Jardim frontal"),
            highlights = listOf("Top 10% do Airbnb", "Nota 5.0 estrelas", "1ª rua atrás do Le Sapé"),
            categoryIcon = "Yard",
            gradientColors = Pair(0xFFC85A32, 0xFFE87A5D),
            floorPointId = "fp_pedestrian_gate",
            imageUrl = "file:///android_asset/house_photos/af2bb15c-883e-4228-babe-c15897967312.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/4f2afea3-14c5-4e1d-80c1-ad49611cfabd.jpeg",
                "file:///android_asset/house_photos/05597ee6-5263-4d79-bd4d-b3f37d4f5103.jpeg",
                "file:///android_asset/house_photos/95fcbad5-bd73-4fa6-bc8c-fd1f437b9078.jpeg",
                "file:///android_asset/house_photos/5adc003b-d485-4e64-8b92-195c72a76605.jpeg"
            ),
            photos = listOf(
                RoomPhoto("af2bb15c-883e-4228-babe-c15897967312.jpeg", "Fachada frontal da casa com escadaria de pedras e palmeiras imperiais", isPrimary = true),
                RoomPhoto("4f2afea3-14c5-4e1d-80c1-ad49611cfabd.jpeg", "Rampa de acesso à garagem e escadaria social em pedra", isPrimary = false),
                RoomPhoto("05597ee6-5263-4d79-bd4d-b3f37d4f5103.jpeg", "Varanda de entrada da casa com poltronas de vime marrom e plantas", isPrimary = false),
                RoomPhoto("95fcbad5-bd73-4fa6-bc8c-fd1f437b9078.jpeg", "Vista superior da rampa, jardins frontais e montanhas ao fundo", isPrimary = false),
                RoomPhoto("5adc003b-d485-4e64-8b92-195c72a76605.jpeg", "Jardim e gramado frontal com cerca viva", isPrimary = false)
            ),
            manualSectionId = 1
        ),
        RoomItem(
            id = "room_living_1",
            title = "Sala de Estar Principal & Piano",
            floor = "1º Andar (Térreo)",
            subtitle = "Ambiente acolhedor com piano acústico e luz natural",
            bedConfig = "Sofá aconchegante • Piano vertical clássico",
            description = "Sala de estar ampla com dois ambientes integrados, piano acústico clássico vertical afinado à disposição dos hóspedes, poltronas confortáveis e porta com vista para as montanhas.",
            amenities = listOf("Piano clássico", "Sofá confortável", "Dois ambientes", "Mesa de centro"),
            highlights = listOf("Piano disponível para hóspedes", "Luz natural abundante"),
            categoryIcon = "MusicNote",
            gradientColors = Pair(0xFF4A3E3D, 0xFF8D7B79),
            floorPointId = "fp_piano",
            imageUrl = "file:///android_asset/house_photos/901c8a9c-e1d9-42d7-b61e-96363e9681eb.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/0ebb0426-ca5b-478f-b826-b8c100830367.jpeg",
                "file:///android_asset/house_photos/92f8ad6a-11df-47c3-96cd-2a2e67455b69.jpeg",
                "file:///android_asset/house_photos/e9981121-13c3-4ff3-b70d-8e554f9279d4.jpeg"
            ),
            photos = listOf(
                RoomPhoto("901c8a9c-e1d9-42d7-b61e-96363e9681eb.jpeg", "Sala de estar e música com piano acústico de madeira e divã com almofadas", isPrimary = true),
                RoomPhoto("0ebb0426-ca5b-478f-b826-b8c100830367.jpeg", "Sala de estar iluminada com sofá bege, poltronas e porta aberta para a varanda", isPrimary = false),
                RoomPhoto("92f8ad6a-11df-47c3-96cd-2a2e67455b69.jpeg", "Piano vertical afinado com banqueta, poltrona de madeira e manta vermelha", isPrimary = false),
                RoomPhoto("e9981121-13c3-4ff3-b70d-8e554f9279d4.jpeg", "Sala social com arco arquitetônico, mesa de vidro e escada para o 2º andar", isPrimary = false)
            ),
            manualSectionId = 4
        ),
        RoomItem(
            id = "room_kitchen_dining",
            title = "Cozinha Completa & Sala de Jantar",
            floor = "1º Andar (Térreo)",
            subtitle = "Cozinha espaçosa, copa integrada e acesso ao solarium",
            bedConfig = "Mesa de jantar 4 lugares • Utensílios completos",
            description = "Cozinha espaçosa com bancada em alvenaria e azulejos claros, armários embutidos de madeira e vidro, fogão 4 bocas, forno, Air Fryer, liquidificador, mesa de jantar para 4 pessoas e porta de correr envidraçada que abre diretamente para a varanda ensolarada.",
            amenities = listOf("Geladeira", "Air Fryer", "Fogão & Forno", "Torradeira", "Liquidificador", "Filtro de café"),
            highlights = listOf("Café, açúcar e chás cortesia", "Air Fryer prática"),
            categoryIcon = "Kitchen",
            gradientColors = Pair(0xFFB3541E, 0xFFE08D58),
            floorPointId = "fp_dining_table",
            imageUrl = "file:///android_asset/house_photos/901a4bc4-72b5-406a-94db-1a393b0a4b4c.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/0a107ab0-515f-4433-b4c1-7365e70d1997.jpeg"
            ),
            photos = listOf(
                RoomPhoto("901a4bc4-72b5-406a-94db-1a393b0a4b4c.jpeg", "Cozinha ampla equipada com armários, fogão, bancada e mesa de jantar", isPrimary = true),
                RoomPhoto("0a107ab0-515f-4433-b4c1-7365e70d1997.jpeg", "Copa com mesa para refeições e porta de vidro aberta para o solarium ensolarado", isPrimary = false)
            ),
            manualSectionId = 6
        ),
        RoomItem(
            id = "room_bedroom_1",
            title = "Quarto 1 (Térreo - Casal Clássico)",
            floor = "1º Andar (Térreo)",
            subtitle = "Quarto térreo com cama de casal em madeira maciça",
            bedConfig = "1 Cama de Casal em Madeira Maciça",
            description = "Ideal para quem prefere evitar escadas. Quarto bem ventilado no piso inferior com cama de casal trabalhada em madeira clara, dois criados-mudos de gavetas com abajures e acesso fácil ao banheiro térreo.",
            amenities = listOf("1 Cama de casal de madeira", "2 Criados-mudos com abajur", "Janela ventilada", "Próximo ao banheiro térreo"),
            highlights = listOf("Acesso sem escadas", "Travesseiros macios e colcha floral"),
            categoryIcon = "Bed",
            gradientColors = Pair(0xFF2E6F50, 0xFF65A785),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/0c3ef2f5-1b19-48fc-9166-28c5564b0c11.jpeg",
            additionalImages = emptyList(),
            photos = listOf(
                RoomPhoto("0c3ef2f5-1b19-48fc-9166-28c5564b0c11.jpeg", "Cama de casal em madeira maciça com colcha floral azul e criados-mudos", isPrimary = true)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_bathroom_1",
            title = "Banheiro Social Térreo",
            floor = "1º Andar (Térreo)",
            subtitle = "Banheiro completo no primeiro pavimento",
            bedConfig = "Chuveiro elétrico quente • Pia com coluna",
            description = "Banheiro completo no térreo com azulejos claros, pia com coluna e saboneteira, espelho com armarinho suspenso, toalhas de banho felpudas bordadas à mão 'Ela', vaso com assento de madeira e chuveiro elétrico quente.",
            amenities = listOf("Chuveiro elétrico quente", "Toalhas bordadas", "Sabonetes", "Ventilação natural"),
            highlights = listOf("Por favor, descartar papéis na lixeira"),
            categoryIcon = "Bathtub",
            gradientColors = Pair(0xFF1971C2, 0xFF4DABF7),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/1992655d-b7f6-42d7-984f-eb1e90f259bf.jpeg",
            additionalImages = emptyList(),
            photos = listOf(
                RoomPhoto("1992655d-b7f6-42d7-984f-eb1e90f259bf.jpeg", "Banheiro térreo com pia de coluna, espelho, toalhas bordadas e chuveiro", isPrimary = true)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_tv_lounge",
            title = "Sala de TV & Estar Superior",
            floor = "2º Andar (Piso Superior)",
            subtitle = "Espaço multimídia com Sky Net, Chromecast e futon relax",
            bedConfig = "Futon aconchegante • TV com canais por assinatura",
            description = "Sala íntima e acolhedora no segundo piso com TV tela plana sobre rack moderno, canais Sky Net (HDMI 2), Chromecast para espelhamento (HDMI 4), iluminação aconchegante com abajur e futon com almofadas no chão para relaxar ou assistir filmes.",
            amenities = listOf("TV Smart com Sky Net", "Chromecast", "Secador de cabelo", "Futon & Almofadas"),
            highlights = listOf("Secador na prateleira esquerda do rack", "Controle maior da TV e menor da Sky"),
            categoryIcon = "Tv",
            gradientColors = Pair(0xFF7048E8, 0xFF9775FA),
            floorPointId = "fp_tv_controls",
            imageUrl = "file:///android_asset/house_photos/0ae89d04-e9af-4bf2-9410-72225718fb4d.jpeg",
            additionalImages = emptyList(),
            photos = listOf(
                RoomPhoto("0ae89d04-e9af-4bf2-9410-72225718fb4d.jpeg", "Sala de TV no 2º andar com rack, canais por assinatura, abajur e futon relax", isPrimary = true)
            ),
            manualSectionId = 4
        ),
        RoomItem(
            id = "room_bedroom_2",
            title = "Quarto 2 (Casal Superior - Verde Menta)",
            floor = "2º Andar (Piso Superior)",
            subtitle = "Quarto de casal com parede verde menta e vista do jardim",
            bedConfig = "1 Cama de Casal Box Confortável",
            description = "Quarto tranquilo e relaxante no andar superior com parede de destaque em tom verde menta suave, cama de casal box com colcha floral, dois criados-mudos de madeira com abajures e janela com vista para os telhados coloniais e copas das árvores.",
            amenities = listOf("1 Cama de casal box", "2 Criados-mudos com abajur", "Janela arejada", "Ambiente relaxante"),
            highlights = listOf("Parede verde menta calmante", "Silencioso e fresco"),
            categoryIcon = "Bed",
            gradientColors = Pair(0xFF0CA678, 0xFF38D9A9),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/69700cfb-5d0c-4845-82e3-2edd1ca7b605.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/e03db734-4417-419b-b192-6b5e6b52ffcc.jpeg"
            ),
            photos = listOf(
                RoomPhoto("69700cfb-5d0c-4845-82e3-2edd1ca7b605.jpeg", "Cama box de casal com colcha floral, parede verde menta e vista para o jardim", isPrimary = true),
                RoomPhoto("e03db734-4417-419b-b192-6b5e6b52ffcc.jpeg", "Visão frontal da cama de casal, criados-mudos e janela para telhados coloniais", isPrimary = false)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_bedroom_3",
            title = "Quarto 3 (Solteiro Superior c/ Armário & Enxoval)",
            floor = "2º Andar (Piso Superior)",
            subtitle = "Quarto com cama de solteiro e guarda-roupa com enxoval reserva",
            bedConfig = "1 Cama de Solteiro (+ Armário c/ Enxoval Reserva)",
            description = "Quarto de solteiro do piso superior com cama confortável, criado-mudo com abajur e um grande guarda-roupa branco de 4 portas onde ficam guardadas as toalhas extras, roupas de cama adicionais e cobertores quentes para todos os hóspedes. Janela com vista para árvores e pomar.",
            amenities = listOf("1 Cama de solteiro", "Armário branco com toalhas extras", "Cobertores quentes", "Vista do pomar"),
            highlights = listOf("Armário branco com toalhas e lençóis extras"),
            categoryIcon = "Bed",
            gradientColors = Pair(0xFF099268, 0xFF20C997),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/ea62677c-2974-4e7b-8a40-016f5d196dab.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/376987e2-8e3c-4645-9c04-537c7b0b9d60.jpeg"
            ),
            photos = listOf(
                RoomPhoto("ea62677c-2974-4e7b-8a40-016f5d196dab.jpeg", "Cama de solteiro, criado-mudo e armário branco de 4 portas com enxoval reserva", isPrimary = true),
                RoomPhoto("376987e2-8e3c-4645-9c04-537c7b0b9d60.jpeg", "Janela do quarto com vista aberta para o pomar e quintal verdejante", isPrimary = false)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_bedroom_4",
            title = "Quarto 4 (Superior c/ Bicama & Vista Panorâmica)",
            floor = "2º Andar (Piso Superior)",
            subtitle = "Quarto amplo com bicama, piso de madeira e vista espetacular",
            bedConfig = "Bicama aconchegante (ou Casal/Queen)",
            description = "Quarto iluminado e espaçoso com piso laminado de madeira, bicama com colcha salmão, estante provençal branca com prateleiras e toalhas, cadeira rústica azul e uma ampla janela envidraçada de ponta a ponta com vista panorâmica deslumbrante das montanhas de São Lourenço.",
            amenities = listOf("Bicama confortável", "Piso laminado de madeira", "Janela panorâmica", "Vista das montanhas"),
            highlights = listOf("Vista panorâmica exuberante das montanhas"),
            categoryIcon = "Bed",
            gradientColors = Pair(0xFF1098AD, 0xFF3BC9DB),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/a35faf2d-01f8-423c-99a5-72626928b4ed.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/2e5dccd5-90d9-4d54-80ef-e4287a7ca619.jpeg",
                "file:///android_asset/house_photos/d8b280f4-07fd-4880-809d-d71d10773b07.jpeg",
                "file:///android_asset/house_photos/c7be1b3a-8c32-4562-9e51-cf2c88ec315c.jpeg"
            ),
            photos = listOf(
                RoomPhoto("a35faf2d-01f8-423c-99a5-72626928b4ed.jpeg", "Cama de casal com estante branca, cadeira azul e janela com vista das montanhas", isPrimary = true),
                RoomPhoto("2e5dccd5-90d9-4d54-80ef-e4287a7ca619.jpeg", "Quarto amplo com piso de madeira, bicama com colcha salmão e vista panorâmica", isPrimary = false),
                RoomPhoto("d8b280f4-07fd-4880-809d-d71d10773b07.jpeg", "Janela ampla envidraçada com vista deslumbrante das colinas e vegetação", isPrimary = false),
                RoomPhoto("c7be1b3a-8c32-4562-9e51-cf2c88ec315c.jpeg", "Bicama branca com almofadas aconchegantes e criado-mudo com abajur", isPrimary = false)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_bathroom_2",
            title = "Banheiro Social Superior",
            floor = "2º Andar (Piso Superior)",
            subtitle = "Banheiro completo para atender aos quartos do 2º piso",
            bedConfig = "Chuveiro elétrico quente • Espelho oval e pia cinza",
            description = "Banheiro no andar superior com revestimento cerâmico branco, pia cinza com espelho oval decorativo, toalhas de banho limpas bordadas 'Ela', tapete e box com chuveiro elétrico quente para relaxar após os passeios.",
            amenities = listOf("Chuveiro elétrico", "Toalhas de banho", "Espelho decorativo", "Janela externa"),
            highlights = listOf("Água quentinha e toalhas limpas"),
            categoryIcon = "Bathtub",
            gradientColors = Pair(0xFF1C7ED6, 0xFF4DABF7),
            floorPointId = "fp_fans",
            imageUrl = "file:///android_asset/house_photos/4370b47c-6622-45fd-bd45-8792cf111bf3.jpeg",
            additionalImages = emptyList(),
            photos = listOf(
                RoomPhoto("4370b47c-6622-45fd-bd45-8792cf111bf3.jpeg", "Banheiro superior com azulejos brancos, espelho oval, toalhas bordadas e box", isPrimary = true)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_balconies_hammock",
            title = "Varandas & Rede de Descanso",
            floor = "Área Externa & Varandas",
            subtitle = "Varanda superior com rede amarela e vista para as montanhas",
            bedConfig = "Rede de descanso artesanal instalada",
            description = "Varanda espaçosa no piso superior com piso cerâmico rústico marrom, rede de descanso amarela com franjas artesanais de tear, parapeito trabalhado em ferro branco e vista deslumbrante das copas das árvores e do pôr do sol nas colinas.",
            amenities = listOf("Rede de descanso", "Sombra fresca", "Vista para as montanhas", "Canto dos pássaros"),
            highlights = listOf("Atenção ao degrau alto próximo à cozinha", "Perfeita para descansar e ler um livro"),
            categoryIcon = "Deck",
            gradientColors = Pair(0xFFE8590C, 0xFFFF922B),
            floorPointId = "fp_hammock",
            imageUrl = "file:///android_asset/house_photos/58887f02-3330-4d76-a857-bba536db6155.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/41690046-ab13-4476-8ef6-ac484ff87bc7.jpeg",
                "file:///android_asset/house_photos/be4f9f84-9c37-40b9-9ddd-760c9e28f97a.jpeg"
            ),
            photos = listOf(
                RoomPhoto("58887f02-3330-4d76-a857-bba536db6155.jpeg", "Varanda superior com rede amarela de tear artesanal e vista para o jardim", isPrimary = true),
                RoomPhoto("41690046-ab13-4476-8ef6-ac484ff87bc7.jpeg", "Rede de descanso sob o sol radiante e céu azul das montanhas mineiras", isPrimary = false),
                RoomPhoto("be4f9f84-9c37-40b9-9ddd-760c9e28f97a.jpeg", "Vista panorâmica espetacular das colinas verdes através da janela", isPrimary = false)
            ),
            manualSectionId = 4
        ),
        RoomItem(
            id = "room_patio_laundry",
            title = "Solarium Panorâmico 180° & Mirante",
            floor = "Área Externa & Solarium",
            subtitle = "Pátio em pedra São Tomé com espreguiçadeiras e vista aberta",
            bedConfig = "Espreguiçadeiras de descanso • Piso São Tomé",
            description = "O terraço que dá nome à casa! Amplo solarium revestido em mosaico de pedras de São Tomé, jardineira com agaves, parapeito branco decorativo, espreguiçadeiras confortáveis de vime marrom voltadas para a serra e vista panorâmica de 180° inesquecível.",
            amenities = listOf("Vista 180° das montanhas", "Espreguiçadeiras", "Piso em pedra São Tomé", "Acesso pela copa/cozinha"),
            highlights = listOf("Pôr do sol incrível", "Cuidado ao pisar caso o chão esteja molhado"),
            categoryIcon = "Deck",
            gradientColors = Pair(0xFF5C7CFA, 0xFF748FFC),
            floorPointId = "fp_washing_machine",
            imageUrl = "file:///android_asset/house_photos/b5fe9b8f-2eed-4303-a29e-123ec520e266.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/cc1e572e-e523-44da-8e76-d6287b54f548.jpeg",
                "file:///android_asset/house_photos/cc042dc9-b8d6-4086-b4ab-641878a23f2d.jpeg",
                "file:///android_asset/house_photos/589a2a60-68f7-4fc3-bd94-2bdaa09304ef.jpeg",
                "file:///android_asset/house_photos/f5655d41-d6f0-49f2-abf7-9fa41844dcb1.jpeg"
            ),
            photos = listOf(
                RoomPhoto("b5fe9b8f-2eed-4303-a29e-123ec520e266.jpeg", "Solarium panorâmico com piso de pedra São Tomé, agaves e vista 180° do vale", isPrimary = true),
                RoomPhoto("cc1e572e-e523-44da-8e76-d6287b54f548.jpeg", "Espreguiçadeiras de vime marrom no solarium voltadas para as montanhas", isPrimary = false),
                RoomPhoto("cc042dc9-b8d6-4086-b4ab-641878a23f2d.jpeg", "Mirante da varanda com poltrona de descanso voltada para a serra", isPrimary = false),
                RoomPhoto("589a2a60-68f7-4fc3-bd94-2bdaa09304ef.jpeg", "Piso em pedra natural e guarda-corpo branco com corações de ferro", isPrimary = false),
                RoomPhoto("f5655d41-d6f0-49f2-abf7-9fa41844dcb1.jpeg", "Porta balcão de vidro saindo diretamente para o solarium e montanhas", isPrimary = false)
            ),
            manualSectionId = 5
        ),
        RoomItem(
            id = "room_garage_entry",
            title = "Garagem Privativa & Acessos",
            floor = "Pátio Frontal",
            subtitle = "3 vagas de garagem (1 coberta) e portão automático",
            bedConfig = "3 Vagas de carro (1 coberta sob alpendre)",
            description = "Estacionamento privativo para até 3 veículos com portão eletrônico acionado pelo botão superior direito do controle, rampa em pedras, além de portão social para pedestres com trinco e chave tetra.",
            amenities = listOf("Portão automático", "1 vaga coberta", "2 vagas descobertas", "Portão de pedestres"),
            highlights = listOf("Botão superior direito abre o portão"),
            categoryIcon = "DirectionsCar",
            gradientColors = Pair(0xFF343A40, 0xFF495057),
            floorPointId = "fp_garage_covered",
            imageUrl = "file:///android_asset/house_photos/4f2afea3-14c5-4e1d-80c1-ad49611cfabd.jpeg",
            additionalImages = listOf(
                "file:///android_asset/house_photos/af2bb15c-883e-4228-babe-c15897967312.jpeg",
                "file:///android_asset/house_photos/95fcbad5-bd73-4fa6-bc8c-fd1f437b9078.jpeg"
            ),
            photos = listOf(
                RoomPhoto("4f2afea3-14c5-4e1d-80c1-ad49611cfabd.jpeg", "Rampa em pedra até o portão branco da garagem e escadaria social", isPrimary = true),
                RoomPhoto("af2bb15c-883e-4228-babe-c15897967312.jpeg", "Fachada frontal completa com portão e jardins floridos", isPrimary = false),
                RoomPhoto("95fcbad5-bd73-4fa6-bc8c-fd1f437b9078.jpeg", "Vista aérea do pátio e rampa de veículos até o portão branco", isPrimary = false)
            ),
            manualSectionId = 9
        )
    )
}
