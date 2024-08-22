package com.cathay.bank.coindeskhelper.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;
import com.cathay.bank.coindeskhelper.vos.RestResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/bitcoin")
public interface IBitcoinController {
    @Operation(summary = "Find Bitcoin by language",
            description = "Retrieve a list of Bitcoin information based on the specified language.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved Bitcoin information"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{language}")
    ResponseEntity<RestResult<List<BitCoinInfoByLanguage>>> findBitcoinByLanguage(
            @PathVariable String language);

    @Operation(summary = "Add or update Bitcoin translation",
            description = "Add a new Bitcoin translation or update an existing one based on the provided settings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully added or updated Bitcoin translation"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request, possibly due to missing or incorrect data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/translation/add-or-update")
    ResponseEntity<RestResult<BitcoinTranslation>> addOrUpdateTranslation(
            @Valid @RequestBody BitcoinTranslationSetting setting) throws BitcoinException;

    @Operation(summary = "Delete Bitcoin translation",
            description = "Delete a Bitcoin translation based on the provided code and language.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted Bitcoin translation"),
            @ApiResponse(responseCode = "400", description = "Invalid code"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/translation/{code}/{language}")
    ResponseEntity<RestResult<Boolean>> deleteTranslation(@PathVariable String code,
            @PathVariable String language) throws BitcoinException;


    @Operation(summary = "Delete Bitcoin",
            description = "Delete a Bitcoin based on the provided code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted Bitcoind"),
            @ApiResponse(responseCode = "400", description = "Invalid code"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{code}")
    ResponseEntity<RestResult<Boolean>> delete(@PathVariable String code) throws BitcoinException;

}
