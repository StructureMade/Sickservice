package de.structuremade.ms.sicksercvice.api.routes;

import com.google.gson.Gson;
import de.structuremade.ms.sicksercvice.api.json.ApologizeUser;
import de.structuremade.ms.sicksercvice.api.json.CreateSick;
import de.structuremade.ms.sicksercvice.api.json.answer.GetSick;
import de.structuremade.ms.sicksercvice.api.service.SickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/service/sick")
public class SickRoute {

    @Autowired
    private SickService sickService;

    private Gson gson;

    @CrossOrigin
    @PostMapping("/create")
    public void create(@RequestBody CreateSick cs, HttpServletResponse response){
        switch (sickService.create(cs)){
            case 0 -> response.setStatus(HttpStatus.CREATED.value());
            case 1 -> response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @CrossOrigin
    @PutMapping("/apologize/{userid}")
    public void apologize(@PathVariable String userid, @RequestBody ApologizeUser au, HttpServletResponse response, HttpServletRequest request){
        switch (sickService.apologize(userid, au.getDate(),au.getCommentary(), request.getHeader("Authorization").substring(7))){
            case 0 -> response.setStatus(HttpStatus.OK.value());
            case 1 -> response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            case 2 -> response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @CrossOrigin
    @GetMapping("/get/{userid}")
    public Object get(@PathVariable String userid, HttpServletResponse response, HttpServletRequest request){
        GetSick gs = sickService.get(userid, request.getHeader("Authorization").substring(7));
        if (gs != null){
            if(!gs.getSicks().isEmpty()){
                response.setStatus(HttpStatus.OK.value());
                return gson.toJson(gs);
            }else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return null;
    }




}
