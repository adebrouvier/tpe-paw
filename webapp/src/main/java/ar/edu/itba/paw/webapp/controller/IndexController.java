package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.webapp.form.TournamentSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TournamentService ts;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("searchForm") final TournamentSearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("tournaments",ts.findFeaturedTournaments());
        mav.addObject("tournamentNames", tournamentToCellString(ts.findTournamentNames()));
        return mav;
    }

    private String tournamentToCellString(List<String> list){
        StringBuilder sb = new StringBuilder("{");

        if (list.isEmpty()){
            return sb.append("}").toString();
        }

        for(String tournament : list) {
            sb.append("\"");
            sb.append(tournament);
            sb.append("\"");
            sb.append(": null,");
        }

        sb.append("\"other\":null}");

        return sb.toString();
    }

    @RequestMapping(value = "/searchtournament", method = { RequestMethod.GET })
    public ModelAndView searchtournament(@Valid @ModelAttribute("searchForm")
                               final TournamentSearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }

        String search = form.getQuery();
        String param = "";
        try {
            param = URLEncoder.encode(search,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/search?query=" + param);
    }

//    @ModelAttribute("userId")
//    public Integer loggedUser(final HttpSession session) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentName = authentication.getName();
//        Integer userId = (Integer) session.getAttribute(currentName);
 //       return (Integer) session.getAttribute(currentName);
 //   }
}
