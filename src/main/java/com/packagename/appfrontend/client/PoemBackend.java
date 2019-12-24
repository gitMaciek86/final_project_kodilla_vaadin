package com.packagename.appfrontend.client;

import com.packagename.appfrontend.domain.PoemDto;
import com.packagename.appfrontend.domain.FullPoetryDto;
import com.packagename.appfrontend.domain.TitlesFromLibrary;
import com.packagename.appfrontend.domain.TitlesFromStore;
import com.packagename.appfrontend.encode.Encoder;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PoemBackend {

    public TitlesFromStore getAllPoemsFromStore() {
        try {
            HttpResponse<TitlesFromStore> httpResponse = Unirest.get("http://localhost:8080/v1/poem/alltitles")
                    .asObject(TitlesFromStore.class);
            return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public TitlesFromLibrary[] getAllPoemsFromLibrary() {
        try {
            HttpResponse<TitlesFromLibrary[]> httpResponse = Unirest.get("http://localhost:8080/v1/poem/getPoems")
                    .asObject(TitlesFromLibrary[].class);
             return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public PoemDto[] getAllPoemsForUser(String userId) {
        try {
            HttpResponse<PoemDto[]> httpResponse = Unirest.get("http://localhost:8080/v1/poem/alltitlesforuser/"+userId)
                    .asObject(PoemDto[].class);
            return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public String translatePoem(String poemTitle) {
        try {
            HttpResponse<String> httpResponse = Unirest.get("http://localhost:8080/v1/poem/translatePoem")
                    .queryString("q", poemTitle)
                    .asString();
            return httpResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public FullPoetryDto buyPoem(String poemTitle, String userId) {
        PoemDto poemDto = new PoemDto();
        poemDto.setTitle(Encoder.encode(poemTitle));
        try {
            HttpResponse<FullPoetryDto> FullPoetryDtoResponse = Unirest.post("http://localhost:8080/v1/poem/buyPoem/" + userId)
                    .header("Content-Type", "application/json")
                    .body(poemDto)
                    .asObject(FullPoetryDto.class);
            return FullPoetryDtoResponse.getBody();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public void removePoemForUser(Long poemId, String userSessionId) {
        try {
            Unirest.delete("http://localhost:8080/v1/poem/deletePoem/" + poemId+"/"+userSessionId)
                    .header("Content-Type", "application/json")
                    .asString();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
