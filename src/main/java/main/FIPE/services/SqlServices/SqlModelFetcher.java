package main.FIPE.services.SqlServices;

import main.FIPE.models.BrandsEnum;
import main.FIPE.models.GenericItem;
import main.FIPE.models.ModelData;
import main.FIPE.services.database.ConnectionFactory;
import main.FIPE.services.interfaces.IModelFetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlModelFetcher implements IModelFetcher {
    @Override
    public ModelData getModelsFromBrand(BrandsEnum brand){
        //fazer listagem dos veículos da marca especificada
        if (brand == null || brand == BrandsEnum.UNDEFINED) {
            return null;
        }

        // nao concatenar parametros
        String sql = "SELECT * FROM models WHERE brand_code = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, brand.getId());

            ResultSet resultSet = stmt.executeQuery();

            List<GenericItem> models = new ArrayList<>();

            while(resultSet.next()){

                GenericItem item = new GenericItem();

                item.setCode(resultSet.getString("fipe_code"));
                item.setName(resultSet.getString("name"));
                models.add(item);
            }
            ModelData modelData = new ModelData();
            modelData.setFipeModelCode(brand.getId());
            modelData.setModels(models);

            return modelData;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
