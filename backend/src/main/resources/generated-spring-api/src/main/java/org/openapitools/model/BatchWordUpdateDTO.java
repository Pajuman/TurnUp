package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.model.NewWordDTO;
import org.openapitools.model.WordDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BatchWordUpdateDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-02T23:32:11.252341600+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class BatchWordUpdateDTO {

  @Valid
  private List<@Valid NewWordDTO> newWords = new ArrayList<>();

  @Valid
  private List<@Valid WordDTO> updatedWords = new ArrayList<>();

  @Valid
  private List<UUID> deletedWordIds = new ArrayList<>();

  public BatchWordUpdateDTO newWords(List<@Valid NewWordDTO> newWords) {
    this.newWords = newWords;
    return this;
  }

  public BatchWordUpdateDTO addNewWordsItem(NewWordDTO newWordsItem) {
    if (this.newWords == null) {
      this.newWords = new ArrayList<>();
    }
    this.newWords.add(newWordsItem);
    return this;
  }

  /**
   * Get newWords
   * @return newWords
   */
  @Valid 
  @Schema(name = "newWords", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newWords")
  public List<@Valid NewWordDTO> getNewWords() {
    return newWords;
  }

  public void setNewWords(List<@Valid NewWordDTO> newWords) {
    this.newWords = newWords;
  }

  public BatchWordUpdateDTO updatedWords(List<@Valid WordDTO> updatedWords) {
    this.updatedWords = updatedWords;
    return this;
  }

  public BatchWordUpdateDTO addUpdatedWordsItem(WordDTO updatedWordsItem) {
    if (this.updatedWords == null) {
      this.updatedWords = new ArrayList<>();
    }
    this.updatedWords.add(updatedWordsItem);
    return this;
  }

  /**
   * Get updatedWords
   * @return updatedWords
   */
  @Valid 
  @Schema(name = "updatedWords", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedWords")
  public List<@Valid WordDTO> getUpdatedWords() {
    return updatedWords;
  }

  public void setUpdatedWords(List<@Valid WordDTO> updatedWords) {
    this.updatedWords = updatedWords;
  }

  public BatchWordUpdateDTO deletedWordIds(List<UUID> deletedWordIds) {
    this.deletedWordIds = deletedWordIds;
    return this;
  }

  public BatchWordUpdateDTO addDeletedWordIdsItem(UUID deletedWordIdsItem) {
    if (this.deletedWordIds == null) {
      this.deletedWordIds = new ArrayList<>();
    }
    this.deletedWordIds.add(deletedWordIdsItem);
    return this;
  }

  /**
   * Get deletedWordIds
   * @return deletedWordIds
   */
  @Valid 
  @Schema(name = "deletedWordIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deletedWordIds")
  public List<UUID> getDeletedWordIds() {
    return deletedWordIds;
  }

  public void setDeletedWordIds(List<UUID> deletedWordIds) {
    this.deletedWordIds = deletedWordIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchWordUpdateDTO batchWordUpdateDTO = (BatchWordUpdateDTO) o;
    return Objects.equals(this.newWords, batchWordUpdateDTO.newWords) &&
        Objects.equals(this.updatedWords, batchWordUpdateDTO.updatedWords) &&
        Objects.equals(this.deletedWordIds, batchWordUpdateDTO.deletedWordIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(newWords, updatedWords, deletedWordIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchWordUpdateDTO {\n");
    sb.append("    newWords: ").append(toIndentedString(newWords)).append("\n");
    sb.append("    updatedWords: ").append(toIndentedString(updatedWords)).append("\n");
    sb.append("    deletedWordIds: ").append(toIndentedString(deletedWordIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

