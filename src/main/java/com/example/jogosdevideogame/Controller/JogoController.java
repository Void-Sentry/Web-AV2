package com.example.jogosdevideogame.Controller;

import com.example.jogosdevideogame.Model.Jogo;
import com.example.jogosdevideogame.Service.FileStorageService;
import com.example.jogosdevideogame.Service.JogoService;
import com.example.jogosdevideogame.Util.Mensagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class JogoController {

    private JogoService service;
    private FileStorageService fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setService(JogoService jogo){
        this.service = jogo;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getHome(Model model, HttpServletResponse response){
        var listaJogos = service.findAll();
        model.addAttribute("listaJogos", listaJogos);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss:SSS");
        Date date = new Date();
        Cookie c = new Cookie("visita", dateFormat.format(date));

        c.setMaxAge(60 * 60 * 24);

        response.addCookie(c);
        return "index";
    }

    @RequestMapping("/AdicionarCarrinho/{id}")
    public String AdicionarCarrinho(@PathVariable(name = "id") Long id, HttpServletRequest request, RedirectAttributes attributes){

        HttpSession session = request.getSession();

        if(session.getAttribute("carrinho") == null){
            session.setAttribute("carrinho", new ArrayList<Jogo>());
        }

        Jogo jogo = service.findById(id);
        ArrayList<Jogo> j = (ArrayList<Jogo>)session.getAttribute("carrinho");

        j.add(jogo);
        attributes.addAttribute("redirectMessage", " " + jogo.getTitulo() + " was added to your cart");

        return "redirect:/index";
    }

    @RequestMapping("/carrinho")
    public String getCarrinho(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session.getAttribute("carrinho") == null) {
            return "redirect:/";
        }
        else{
            return "carrinho";
        }

    }

    @RequestMapping("/removerCarrinho/{id}")
    public String doRemoverCarrinho(@PathVariable(name = "id") Long id, HttpServletRequest request){
        HttpSession session = request.getSession();

        List<Jogo> remover = (List<Jogo>)session.getAttribute("carrinho");
        remover.remove(service.findById(id));

        System.out.println(remover);

        return "redirect:/carrinho";
    }

    @RequestMapping("/admin")
    public String getAdmin(Model model){
        var listaJogos = service.findAll();
        model.addAttribute("listaJogos", listaJogos);
        fileStorageService.loadAll();

        return "admin";
    }

    @RequestMapping("/cadastro")
    public String getFormCadastro(Model model){
        Jogo jogo = new Jogo();
        model.addAttribute("jogo", jogo);
        return "cadastro";
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String doSalvar(@ModelAttribute @Valid Jogo jogo, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            return "cadastro";
        }
        else{
            Integer hashed = hashCode();
            jogo.setImageUri(hashed + file.getOriginalFilename());
            service.save(jogo);
            fileStorageService.save(file, hashed);

            redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso!");
            return "redirect:/admin";
        }
    }

    @RequestMapping("/deletar/{id}")
    public String doDelete(@PathVariable(name = "id") Long id, RedirectAttributes attributes){
        service.delete(id);
        //attributes.addAttribute("Menssagem", Mensagem.DELETADO_COM_SUCESSO);
        return "redirect:/admin";
    }

    @RequestMapping("/editar/{id}")
    public ModelAndView doEdit(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("edicao");
        Jogo jogo = service.findById(id);
        modelAndView.addObject("jogo", jogo);

        return modelAndView;
    }

    @RequestMapping("/finalizarCompra")
    public String doFinalizarCompra(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/index";
    }

    @RequestMapping("/erro")
    public String getErro(){    
        return "index";
    }
}
