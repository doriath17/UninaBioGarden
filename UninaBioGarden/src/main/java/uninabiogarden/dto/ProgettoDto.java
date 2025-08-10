package uninabiogarden.dto;

import java.time.LocalDate;

import uninabiogarden.entities.Lotto;

public record ProgettoDto (
  String nome,
  LocalDate dataInizio,
  LocalDate dataFine, 
  String descrizione, 
  Lotto lotto
) {}

/// le chiavi esterne non sono dei riferimenti agli attuali 
/// oggetti che rappresentano il proprietario o il lotto.
/// Sara responsabilita del servizio scelto cercare a quale 
/// proprietario o lotto corrisponde la chiave esterna e creare
/// l'ogetto completo del modello (dove le chiavi esterne sono 
/// riferimenti a proprietari e lotti)
/// 
/// In generale riferimenti in un dto non vanno messi
/// mentre vanno bene i tipi delle chiavi esterne ad 
/// esempio id lotto invece che un lotto intero.
/// 
/// Questo per mantenere il dto davvero semplice e 
/// per mantenere ben distinte le responsabilita tra 
/// i vari componenti (il client deve solo ricevere data
/// e questa deve essere validata dal service)
/// 
/// Quindi sara un service a verificare che il lotto selezionato
/// e valido oppure no, utilizzando id lotto per trovarlo 
/// tra i lotti del proprietario.