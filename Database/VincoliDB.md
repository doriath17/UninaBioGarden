# Vincoli Database

**Coltivatore** e **Proprietario**

- password not null (utenti possono avere anche la stessa password)
- nome cognome not null (ammessi omonimi)
- bday not null
- email unique e not null
- nazionalità' not null
- num_tel e residenza nullable
- non può esistere un coltivatore che è anche un proprietario (e viceversa)

**Lotto**

- indirizzo e codice lotto insieme devono essere unique e not null
- estensione not null
- nome orto not null
- username_prop not null (un lotto deve avere un proprietario)

**Coltura**

- nome not null
- colture sullo stesso orto non hanno lo stesso nome --> nome unique su un lotto
- descrizione nullable
- tempo di maturazione not null
- data semina not null
- id_lotto not null (una coltura deve essere coltivata su qualche lotto e di conseguenza gestita da un progetto)

**Attività'**

- tipo == null => i campi quantità' devono essere null.
- Se tipo == raccolta quantità/quantità_effettiva e quantità prevista deve essere not null
- Se tipo == semina quantità_prevista deve essere null
- Se tipo == irrigazione quantità_prevista e quantità/quantità_effettiva devono essere null
- Se tipo == preparazione terreno quantità_prevista e quantità/quantità_effettiva devono essere null
- Se tipo == potatura quantità_prevista e quantità/quantità_effettiva devono essere null
- Se tipo == fertilizzazione quantità_prevista e quantità/quantità_effettiva devono essere null
- per tutti gli atri tipi aggiunti in futuro, l utente fornirà i vincoli

- nome not null
- data_inizio not null
- data_inizio non può' essere precedente alla data di inizio del progetto che prevede l'attività'.
- nome e data inizio insieme devono essere unique tra le attività' associate allo stesso progetto
- data_fine nullable --> se stato == COMPLETATO => data_fine deve essere not null
- descrizione nullable
- stato attività not null
- id_progetto not null
- id_coltura not null
- id_coltura deve corrispondere ad una coltura prevista dallo stesso progetto che prevede l'attività'

**Svolta_da**

- un coltivatore può' svolgere una attività' solo se il coltivatore e' associato al lotto che ospita il progetto che prevede l'attività'

**Progetto**

- nome not null
- data inizio not null
- un proprietario non puo avere progetti con lo stesso nome che iniziano nella stessa data
- data fine nullable
- data fine non deve essere precedente alla data di inizio
- una volta settata la data di fine, questa può essere modificata ma può solo essere posticipata
  in particolare non può essere settata nuovamente a NULL
  (il progetto non può riprendere dopo che è finito, questo comporterebbe riassegnargli un lotto e non è supportato)
- non puoi inserire un progetto gia terminato (con data_fine <> NULL)
- descrizione nullable
- username_prop not null

- un lotto può ospitare soltanto un progetto -- un progetto può essere ospitato soltanto da un lotto
- il lotto referenziato deve appartenere allo stesso proprietario del progetto
- su un lotto può esserci soltanto un progetto in corso alla volta (tutti gli altri devono essere terminati)
