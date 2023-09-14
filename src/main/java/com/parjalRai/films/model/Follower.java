import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id 
    private ObjectId followerId;
    @DBRef
    private UserEntity followingUser;
    @DBRef
    private UserEntity follower; 
    
    
    
}