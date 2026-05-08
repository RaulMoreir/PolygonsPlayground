package testFipeProgram;

import main.FIPE.models.GenericItem;
import main.FIPE.services.FilterIModelNamesProgressivelyByName;
import org.jspecify.annotations.NonNull;
import org.testng.annotations.Test;

import java.util.List;
import static org.testng.Assert.*;

public class FilterModelNamesProgressivelyByNameTest {

    //metodo de setter generic item
    private @NonNull GenericItem criarItem(String code, String name){
        GenericItem item = new GenericItem();
        item.setCode(code);
        item.setName(name);
        return item;
    }


    @Test
    public void testConstrutorParaCemPorCentoDeCoverage() throws Exception{
        FilterIModelNamesProgressivelyByName a = new FilterIModelNamesProgressivelyByName();

        List<GenericItem> models = List.of();
        String textoLeilao = "ONIX";

        List<GenericItem> result = a.filterModelsProgressivelyByName(models, textoLeilao);

        assertTrue(result.isEmpty());

    }

    @Test
    public void HB20Desgraçado(){
        //Setup dos recursos necessários
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();

        List<GenericItem> models = List.of(
                criarItem("8452", "HB20 1 Million 1.6 Flex 16V Aut."),
                criarItem("9906","HB20 Comfort 1.0 Flex 12V Mec."),
                criarItem("9908","HB20 Comfort 1.0 TB Flex 12V Mec"),
                criarItem("10861","HB20 Comfort Plus 1.0 Flex 12V Mec.")
        );
        String textoDoLeilao = "HB20 TB";

        //execução do metodo
        List<GenericItem> result = serv.filterModelsProgressivelyByName(models,textoDoLeilao);

        assertEquals(result.size(), 1);

    }

    @Test
    public void devveFiltrarModeloQuandoExisteMatchCompleto(){
        //Setup dos recursos necessários
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();

        List<GenericItem> models = List.of(
                criarItem("6940", "ONIX LT 1.0 Mec."),
                criarItem("8239","ONIX HATCH ADVANTAGE 1.4 8V Flex 5p Aut."),
                criarItem("7691","ONIX HATCH Joy 1.0 8V Flex 5p Mec."),
                criarItem("6531","ONIX HATCH LTZ 1.4 8V FlexPower 5p Aut.")
        );
        String textoDoLeilao = "ONIX LT1 10 MT";

        //execução do metodo
        List<GenericItem> result = serv.filterModelsProgressivelyByName(models,textoDoLeilao);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(),"ONIX LT 1.0 Mec." );

    }

//    @Test
//    public void textFormaterDeveTratarONIXCorretamente() {
//        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
//
//        String original = "ONIX LT1 10 MT";
//        String formatted = serv.textFormater(original);
//
//        assertTrue(formatted.contains("1.0"));
//        assertTrue(formatted.contains("LT"));
//        assertTrue(formatted.contains("Mec."));
//    }

    @Test
    public void deveRetornarListaCompletaQuandoNenhumMatch() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of(criarItem("6940", "ONIX LT 1.0"));
        //nao existe corolla
        String textoLeilao = "COROLLA 2.0";

        List<GenericItem> result = serv
                .filterModelsProgressivelyByName(models, textoLeilao);

        //o metodo retorna todos os valores quando não dá matches
        assertFalse(result.isEmpty());
    }

    @Test
    public void testHB20QueTaComProblemaNoMatch() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of(criarItem("6940", "ONIX LT 1.0"));


        //nao existe corolla
        String textoLeilao = "COROLLA 2.0";

        List<GenericItem> result = serv
                .filterModelsProgressivelyByName(models, textoLeilao);

        //o metodo retorna todos os valores quando não dá matches
        assertFalse(result.isEmpty());
    }



    @Test
    public void deveRetornarListaOriginalCompletaQuandoTextoVazio() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of(
                criarItem("6940", "ONIX LT 1.0"),
                criarItem("6950", "PRISMA LT")
        );
        String textoLeilao = "";  //ou null?

        List<GenericItem> result = serv.filterModelsProgressivelyByName(models, textoLeilao);

        assertEquals(result.size(), 2);  //devolve Lista completa
    }

    @Test
    public void deveRetornarListaOriginalCompletaQuandoNulo() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of(
                criarItem("6940", "ONIX LT 1.0"),
                criarItem("6950", "PRISMA LT")
        );
        String textoLeilao = null;  //ou null?

        List<GenericItem> result = serv
                .filterModelsProgressivelyByName(models, textoLeilao);

        assertEquals(result.size(), 2);  //devolve Lista completa
    }

    @Test
    public void deveRetornarListaVaziaQuandoModelsVazia() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of();
        String textoLeilao = "ONIX";

        List<GenericItem> result = serv
                .filterModelsProgressivelyByName(models, textoLeilao);

        assertTrue(result.isEmpty());
    }

    @Test
    public void deveFiltrarMesmoComLetrasMinusculas() {
        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
        List<GenericItem> models = List.of(criarItem("6940", "onix lt 1.0"));
        String textoLeilao = "ONIX LT1";

        List<GenericItem> result = serv
                .filterModelsProgressivelyByName(models, textoLeilao);

        assertEquals(result.size(), 1);
    }

//    @Test
//    public void textFormaterJS1DeveHifenizar() {
//        FilterIModelNamesProgressivelyByName serv = new FilterIModelNamesProgressivelyByName();
//        String original = "JS1 CITY CARGO";
//        String formatted = serv.textFormater(original);
//
//        assertTrue(formatted.contains("-"));
//        assertTrue(formatted.contains("JS1"));
//    }

}