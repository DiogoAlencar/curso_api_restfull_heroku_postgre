package curso.api.rest.cursospringrestapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.cursospringrestapi.model.Usuario;
import curso.api.rest.cursospringrestapi.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String excluir(@PathVariable(value = "id") Long id){

        usuarioRepository.deleteById(id);

        return "OK";
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){

        // Associa id do usuário ao registro do telefone no banco
        for (int i=0; i < usuario.getTelefones().size(); i++) {
            usuario.getTelefones().get(i).setUsuario(usuario);
        }

        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){

        // Associa id do usuário ao registro do telefone no banco
        for (int i=0; i < usuario.getTelefones().size(); i++) {
            usuario.getTelefones().get(i).setUsuario(usuario);
        }

        Usuario userSalvao = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(userSalvao, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> buscarUserById(@PathVariable(value = "id") Long id){

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
    public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id,
                                             @PathVariable(value = "venda") Long venda){

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Usuario>> listarUsuarios(){

        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    /* @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity init(@RequestParam(value = "nome", required = true, defaultValue = "Nome não informado") String nome, 
    @RequestParam(value = "idade", required = true, defaultValue = "0") int idade){
        return new ResponseEntity(" Olá "+nome+", idade = "+idade, HttpStatus.OK);
    } */

}
