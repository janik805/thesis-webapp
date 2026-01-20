package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.application.service.profiles.IProfileRepo;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilFachgebiet;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontaktart;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDTO;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDateiValueDTO;
import com.awesome.thesis.persistence.profiles.dtos.ProfilFachgebietDTO;
import com.awesome.thesis.persistence.profiles.dtos.ProfilKontaktDTO;
import com.awesome.thesis.persistence.profiles.dtos.ProfilLinkDTO;
import com.awesome.thesis.persistence.profiles.dtos.ProfilThemaValueDTO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse ist das fachliche Repository für das {@link Profil} Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class ProfileRepoImpl implements IProfileRepo {
  ProfileDBRepository dbRepository;
  
  public ProfileRepoImpl(ProfileDBRepository dbRepository) {
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
  
  //Mapping Profil -> ProfilDTO
  private ProfilDTO toProfilDto(Profil profil) {
    return new ProfilDTO(profil.getId(), profil.getName(),
        translateKontakte(profil.getKontakte()), translateFachgebiete(profil.getFachgebiete()),
        translateLinks(profil.getLinks()), translateThemen(profil.getThemen()),
        translateDateien(profil.getDateien()));
  }
  
  private Set<ProfilDateiValueDTO> translateDateien(Set<ProfilDateiValue> profilDateiValues) {
    return profilDateiValues.stream()
        .map(this::toDateiValueDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilDateiValueDTO toDateiValueDto(ProfilDateiValue profilDateiValue) {
    return new ProfilDateiValueDTO(profilDateiValue.id(), profilDateiValue.name(),
        profilDateiValue.beschreibung());
  }
  
  private Set<ProfilThemaValueDTO> translateThemen(Set<ProfilThemaValue> profilThemaValues) {
    return profilThemaValues.stream()
        .map(this::toThemaValueDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilThemaValueDTO toThemaValueDto(ProfilThemaValue profilThemaValue) {
    return new ProfilThemaValueDTO(profilThemaValue.id(), profilThemaValue.name());
  }
  
  private Set<ProfilLinkDTO> translateLinks(Set<ProfilLink> profilLinks) {
    return profilLinks.stream()
        .map(this::toLinkDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilLinkDTO toLinkDto(ProfilLink profilLink) {
    return new ProfilLinkDTO(profilLink.url(), profilLink.text());
  }
  
  private Set<ProfilFachgebietDTO> translateFachgebiete(Set<String> fachgebiete) {
    return fachgebiete.stream()
        .map(this::toFachgebietDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilFachgebietDTO toFachgebietDto(String profilFachgebiet) {
    return new ProfilFachgebietDTO(profilFachgebiet);
  }
  
  private Set<ProfilKontaktDTO> translateKontakte(Set<ProfilKontakt> kontakt) {
    return kontakt.stream()
        .map(this::toKontaktDto)
        .collect(Collectors.toSet());
  }
  
  private ProfilKontaktDTO toKontaktDto(ProfilKontakt profilKontakt) {
    return new ProfilKontaktDTO(profilKontakt.label(), profilKontakt.wert(),
        profilKontakt.kontaktart().name());
  }
  
  //Mapping ProfilDTO -> Profil
  private Profil toProfil(ProfilDTO profilDto) {
    return new Profil(profilDto.id(), profilDto.name(), translateKontaktDtos(profilDto.kontakte()),
        translateFachgebietDtos(profilDto.fachgebiete()), translateLinkDtos(profilDto.links()),
        translateThemaDtos(profilDto.themen()), translateDateiDtos(profilDto.dateien()));
  }
  
  private Set<ProfilDateiValue> translateDateiDtos(Set<ProfilDateiValueDTO> profilDateiValueDtos) {
    return profilDateiValueDtos.stream()
        .map(this::toDateiValue)
        .collect(Collectors.toSet());
  }
  
  private ProfilDateiValue toDateiValue(ProfilDateiValueDTO profilDateiValueDto) {
    return new ProfilDateiValue(profilDateiValueDto.id(), profilDateiValueDto.name(),
        profilDateiValueDto.beschreibung());
  }
  
  private Set<ProfilThemaValue> translateThemaDtos(Set<ProfilThemaValueDTO> profilThemaValueDtos) {
    return profilThemaValueDtos.stream()
        .map(this::toThemaValue)
        .collect(Collectors.toSet());
  }
  
  private ProfilThemaValue toThemaValue(ProfilThemaValueDTO profilThemaValueDto) {
    return new ProfilThemaValue(profilThemaValueDto.id(), profilThemaValueDto.name());
  }
  
  private Set<ProfilLink> translateLinkDtos(Set<ProfilLinkDTO> profilLinkDtos) {
    return profilLinkDtos.stream()
        .map(this::toLink)
        .collect(Collectors.toSet());
  }
  
  private ProfilLink toLink(ProfilLinkDTO profilLinkDto) {
    return new ProfilLink(profilLinkDto.url(), profilLinkDto.text());
  }
  
  private Set<ProfilFachgebiet> translateFachgebietDtos(Set<ProfilFachgebietDTO> fachgebietDtos) {
    return fachgebietDtos.stream()
        .map(this::toFachgebiet)
        .collect(Collectors.toSet());
  }
  
  private ProfilFachgebiet toFachgebiet(ProfilFachgebietDTO profilFachgebietDto) {
    return new ProfilFachgebiet(profilFachgebietDto.fachgebiet());
  }
  
  private Set<ProfilKontakt> translateKontaktDtos(Set<ProfilKontaktDTO> kontaktDtos) {
    return kontaktDtos.stream()
        .map(this::toKontakt)
        .collect(Collectors.toSet());
  }
  
  private ProfilKontakt toKontakt(ProfilKontaktDTO profilKontaktDto) {
    return new ProfilKontakt(profilKontaktDto.label(), profilKontaktDto.wert(),
        ProfilKontaktart.valueOf(profilKontaktDto.kontaktart()));
  }
}
