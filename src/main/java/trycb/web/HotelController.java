package trycb.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trycb.model.Error;
import trycb.model.IValue;
import trycb.service.Hotel;

@RestController
@RequestMapping("/api/hotel")
@Api(value = "Hotel Controller", description = "manage Hotel data", tags = "Hotel API")
public class HotelController {

    private final Hotel hotelService;


    @Autowired
    public HotelController(Hotel hotelService) {
        this.hotelService = hotelService;
    }

    @ApiOperation(value = "Returns Hotel data", response = Object.class)
    @GetMapping(value="/{description}/{location}/",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends IValue> findHotelsByDescriptionAndLocation(@PathVariable("location") String location, @PathVariable("description") String desc) {
        try {
            return ResponseEntity.ok(hotelService.findHotels(location, desc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(value="/{description}/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends IValue> findHotelsByDescription(@PathVariable("description") String desc) {
        try {
            return ResponseEntity.ok(hotelService.findHotels(desc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(value="/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends IValue> findAllHotels() {
        try {
            return ResponseEntity.ok(hotelService.findAllHotels());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

}
