package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.model.GeneroMusical;
import br.com.resolveai.melodia.domain.repository.GeneroMusicalRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GeneroMusicalService {

    private final GeneroMusicalRepository generoMusicalRepository;

    public List<GeneroMusical> listarTodosGenerosMusicais() {
        return generoMusicalRepository.findAll();
    }

//    @PostConstruct
//    public void popularGenerosMusicais() {
//        if (generoMusicalRepository.count() > 0) {
//            return;
//        }
//
//        Set<String> generosUnicos = new HashSet<>(Arrays.asList(
//            "Rap", "Rock", "Folk", "Non Music", "World", "Blues", "Funk",
//            "Stage And Screen", "Pop", "Jazz", "Soul", "Latin", "Country",
//            "Reggae", "Hip Hop", "Clássico", "Eletrônica", "Sertanejo", "Forró",
//            "MPB", "Pagode", "Samba", "Gospel"
//        ));
//
//        List<GeneroMusical> generosExistentes = generoMusicalRepository.findAll();
//
//        Set<String> generosCadastrados = new HashSet<>(
//            generosExistentes.stream().map(GeneroMusical::getNome).toList()
//        );
//
//        List<GeneroMusical> novosGeneros = generosUnicos.stream()
//            .filter(genero -> !generosCadastrados.contains(genero))
//            .map(nome -> new GeneroMusical(nome, new HashSet<>()))
//            .toList();
//
//        if (!novosGeneros.isEmpty()) {
//            generoMusicalRepository.saveAll(novosGeneros);
//        }
//    }

    public GeneroMusical criarGeneroPadrao() {
        GeneroMusical generoPadrao = new GeneroMusical("Desconhecido", new HashSet<>());
        return generoMusicalRepository.save(generoPadrao);
    }

}
