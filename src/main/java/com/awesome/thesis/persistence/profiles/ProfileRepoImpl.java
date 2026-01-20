package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.application.service.profiles.ProfileRepoI;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilFachgebiet;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontaktart;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDateiValueDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilFachgebietDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilKontaktDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilLinkDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilThemaValueDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse ist das fachliche Repository für das {@link Profil} Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class ProfileRepoImpl implements ProfileRepoI {
  ProfileDbRepository dbRepository;
  
  public ProfileRepoImpl(ProfileDbRepository dbRepository) {
    this.dbRepository = dbRepository;
  }
  
  @Override
  public Profil get(int id) {
    return toProfil(dbRepository.findById(id));
  }
  
  @Override
  public boolean containsKey(int id) {
    return dbRepository.existsById(id);
  }
  
  @Override
  public void save(Profil profil) {
    dbRepository.insert(profil.getId(), profil.getName());
  }
  
  @Override
  public void update(Profil profil) {
    dbRepository.save(toProfilDto(profil));
  }
  
  @Override
  public void delete(int id) {
    dbRepository.deleteById(id);
  }
  
  @Override
  public List<Profil> getAll() {
    return dbRepository.findAll().stream()
        .map(this::toProfil)
        .toList();
  }
  
  //Mapping Profil -> ProfilDto
  private ProfilDto toProfilDto(Profil profil) {
    return new ProfilDto(profil.getId(), profil.getName(),
        translateKontakte(profil.getKontakte()), translateFachgebiete(profil.getFachgebiete()),
        translateLinks(profil.getLinks()), translateThemen(profil.getThemen()),
        translateDateien(profil.getDateien()));
  }
  
  private Set<ProfilDateiValueDto> translateDateien(Set<ProfilDateiValue> profilDateiValues) {
    return profilDateiValues.stream()
        .map(this::toDateiValueDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilDateiValueDto toDateiValueDto(ProfilDateiValue profilDateiValue) {
    return new ProfilDateiValueDto(profilDateiValue.id(), profilDateiValue.name(),
        profilDateiValue.beschreibung());
  }
  
  private Set<ProfilThemaValueDto> translateThemen(Set<ProfilThemaValue> profilThemaValues) {
    return profilThemaValues.stream()
        .map(this::toThemaValueDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilThemaValueDto toThemaValueDto(ProfilThemaValue profilThemaValue) {
    return new ProfilThemaValueDto(profilThemaValue.id(), profilThemaValue.name());
  }
  
  private Set<ProfilLinkDto> translateLinks(Set<ProfilLink> profilLinks) {
    return profilLinks.stream()
        .map(this::toLinkDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilLinkDto toLinkDto(ProfilLink profilLink) {
    return new ProfilLinkDto(profilLink.url(), profilLink.text());
  }
  
  private Set<ProfilFachgebietDto> translateFachgebiete(Set<String> fachgebiete) {
    return fachgebiete.stream()
        .map(this::toFachgebietDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilFachgebietDto toFachgebietDto(String profilFachgebiet) {
    return new ProfilFachgebietDto(profilFachgebiet);
  }
  
  private Set<ProfilKontaktDto> translateKontakte(Set<ProfilKontakt> kontakt) {
    return kontakt.stream()
        .map(this::toKontaktDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilKontaktDto toKontaktDto(ProfilKontakt profilKontakt) {
    return new ProfilKontaktDto(profilKontakt.label(), profilKontakt.wert(),
        profilKontakt.kontaktart().name());
  }
  
  //Mapping ProfilDto -> Profil
  private Profil toProfil(ProfilDto profilDto) {
    return new Profil(profilDto.id(), profilDto.name(), translateKontaktDtos(profilDto.kontakte()),
        translateFachgebietDtos(profilDto.fachgebiete()), translateLinkDtos(profilDto.links()),
        translateThemaDtos(profilDto.themen()), translateDateiDtos(profilDto.dateien()));
  }
  
  private Set<ProfilDateiValue> translateDateiDtos(Set<ProfilDateiValueDto> profilDateiValueDtos) {
    return profilDateiValueDtos.stream()
        .map(this::toDateiValue)
        .collect(Collectors.toSet());
  }
  
  private ProfilDateiValue toDateiValue(ProfilDateiValueDto profilDateiValueDto) {
    return new ProfilDateiValue(profilDateiValueDto.id(), profilDateiValueDto.name(),
        profilDateiValueDto.beschreibung());
  }
  
  private Set<ProfilThemaValue> translateThemaDtos(Set<ProfilThemaValueDto> profilThemaValueDtos) {
    return profilThemaValueDtos.stream()
        .map(this::toThemaValue)
        .collect(Collectors.toSet());
  }
  
  private ProfilThemaValue toThemaValue(ProfilThemaValueDto profilThemaValueDto) {
    return new ProfilThemaValue(profilThemaValueDto.id(), profilThemaValueDto.name());
  }
  
  private Set<ProfilLink> translateLinkDtos(Set<ProfilLinkDto> profilLinkDtos) {
    return profilLinkDtos.stream()
        .map(this::toLink)
        .collect(Collectors.toSet());
  }
  
  private ProfilLink toLink(ProfilLinkDto profilLinkDto) {
    return new ProfilLink(profilLinkDto.url(), profilLinkDto.text());
  }
  
  private Set<ProfilFachgebiet> translateFachgebietDtos(Set<ProfilFachgebietDto> fachgebietDtos) {
    return fachgebietDtos.stream()
        .map(this::toFachgebiet)
        .collect(Collectors.toSet());
  }
  
  private ProfilFachgebiet toFachgebiet(ProfilFachgebietDto profilFachgebietDto) {
    return new ProfilFachgebiet(profilFachgebietDto.fachgebiet());
  }
  
  private Set<ProfilKontakt> translateKontaktDtos(Set<ProfilKontaktDto> kontaktDtos) {
    return kontaktDtos.stream()
        .map(this::toKontakt)
        .collect(Collectors.toSet());
  }
  
  private ProfilKontakt toKontakt(ProfilKontaktDto profilKontaktDto) {
    return new ProfilKontakt(profilKontaktDto.label(), profilKontaktDto.wert(),
        ProfilKontaktart.valueOf(profilKontaktDto.kontaktart()));
  }
}
