package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;
import java.util.List;

/**
 * Interface für fachliches Repository von {@link Thema}.
 */
public interface ThemaRepoI {

  /**
   * Speichert das Thema in der Datenbank.
   *
   * @param thema Der Eingabewert.
   */
  Thema save(Thema thema);

  /**
   * Löscht ein Thema falls vorhanden.
   *
   * @param id Die Id des Themas, das gesucht wird.
   */
  void delete(Integer id);

  /**
   * Fragt nach allen Themen in der Datenbank an.
   *
   * @return Eine Liste von Themen aus der Datenbank.
   */
  List<Thema> getThemen();

  /**
   * Fragt nach einem Thema in der Datenbank an.
   *
   * @param id Die Id des Themas, welches gesucht wird.
   * @return Das Thema mit der Id, welche eingegeben wurde.
   */
  Thema get(int id);

  /**
   * Fragt die Datenbank, ob das Thema mit der Id gespeichert ist.
   *
   * @param id Die Id, welches das gesuchte Thema haben soll.
   * @return True, falls das Thema mit der Id vorhanden ist, sonst false.
   */
  boolean containsKey(Integer id);

}
