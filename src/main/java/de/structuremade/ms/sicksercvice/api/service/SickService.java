package de.structuremade.ms.sicksercvice.api.service;

import de.structuremade.ms.sicksercvice.api.json.CreateSick;
import de.structuremade.ms.sicksercvice.api.json.answer.GetSick;
import de.structuremade.ms.sicksercvice.utils.JWTUtil;
import de.structuremade.ms.sicksercvice.utils.database.entity.Sick;
import de.structuremade.ms.sicksercvice.utils.database.entity.User;
import de.structuremade.ms.sicksercvice.utils.database.repo.SickRepo;
import de.structuremade.ms.sicksercvice.utils.database.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SickService {

    private final Logger LOGGER = LoggerFactory.getLogger(SickService.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    SickRepo sickRepo;

    @Autowired
    JWTUtil jwtUtil;

    public int create(CreateSick cs) {
        Calendar calendar = Calendar.getInstance();
        try {
            Sick sick = new Sick();
            User user = userRepo.getOne(cs.getStudent());
            String[] splitDate = cs.getDate().split("\\.");
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(splitDate[1]));
            calendar.set(Calendar.YEAR, Integer.parseInt(splitDate[2]));
            sick.setDateOfSickness(calendar.getTime());
            sick.setUser(user);
            sickRepo.save(sick);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Couldn't creat Sick", e.fillInStackTrace());
            return 1;
        }
    }

    public int apologize(String userid, String date,String commentary, String jwt){
        Calendar calendar = Calendar.getInstance();
        boolean allowed = false;
        try {
            LOGGER.info("Check if user have allow to apologize");
            for(String children : jwtUtil.extractSpecialClaim(jwt, "children").split(",")){
                if(children.equals(userid)){
                    allowed = true;
                    break;
                }
            }
            if (!allowed){
                LOGGER.info("Have no allow");
                return 2;
            }
            LOGGER.info("Get user");
            User user = userRepo.getOne(userid);
            LOGGER.info("Format date");
            String[] splitDate = date.split("\\.");
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(splitDate[1]));
            calendar.set(Calendar.YEAR, Integer.parseInt(splitDate[2]));
            LOGGER.info("");
            Sick sick = sickRepo.findByUserAndDateOfSickness(user, calendar.getTime());
            sick.setApologized(true);
            sick.setCommentary(commentary);
            sickRepo.save(sick);
            return 0;
        }catch (Exception e){
            LOGGER.error("Couldn't apologize user", e.fillInStackTrace());
            return 1;
        }
    }

    public GetSick get(String userid, String jwt){
        boolean allowed = false;
        String id;
        try {
            LOGGER.info("Check if user have allow to see the sicks");
            id = jwtUtil.extractIdOrEmail(jwt);
            for(String children : jwtUtil.extractSpecialClaim(jwt, "children").split(",")){
                if(children.equals(userid) || id.equals(userid)){
                    allowed = true;
                    break;
                }
            }
            if (!allowed){
                return new GetSick();
            }
            LOGGER.info("Get the sicks and return it to client");
            return new GetSick(sickRepo.findAllByUser(userRepo.getOne(userid)));
        }catch (Exception e) {
            LOGGER.error("Couldn't get the sicks of the user", e.fillInStackTrace());
            return null;
        }

    }
}
