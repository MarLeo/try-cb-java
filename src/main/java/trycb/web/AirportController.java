package trycb.web;

import java.util.List;
import java.util.Map;

import com.couchbase.client.java.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trycb.model.Error;
import trycb.model.IValue;
import trycb.model.Result;
import trycb.service.Airport;

@RestController
@RequestMapping("/api")
@Api(value = "Airport Controller", description = "manage airport data", tags = "Airport API")
public class AirportController {

    private final Bucket bucket;

    @Autowired
    public AirportController(Bucket bucket) {
        this.bucket = bucket;
    }

    @ApiOperation(value = "Returns airport data", response = Object.class)
    @GetMapping("/airports")
    public ResponseEntity<? extends IValue> airports(@RequestParam("search") String search) {
        try {
            Result<List<Map<String, Object>>> result = Airport.findAll(bucket, search);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Error(e.getMessage()));
        }
    }

}
