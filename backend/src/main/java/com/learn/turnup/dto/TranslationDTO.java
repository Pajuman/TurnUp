package com.learn.turnup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * TranslationDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-22T17:50:01.598045700+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class TranslationDTO {

  private String text;

  private String sourceLang;

  private String targetLang;

  public TranslationDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TranslationDTO(String text, String sourceLang, String targetLang) {
    this.text = text;
    this.sourceLang = sourceLang;
    this.targetLang = targetLang;
  }

  public TranslationDTO text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   */
  @NotNull @Size(min = 1, max = 20)
  @Schema(name = "text", example = "kočka", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public TranslationDTO sourceLang(String sourceLang) {
    this.sourceLang = sourceLang;
    return this;
  }

  /**
   * Get sourceLang
   * @return sourceLang
   */
  @NotNull 
  @Schema(name = "sourceLang", example = "CS", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceLang")
  public String getSourceLang() {
    return sourceLang;
  }

  public void setSourceLang(String sourceLang) {
    this.sourceLang = sourceLang;
  }

  public TranslationDTO targetLang(String targetLang) {
    this.targetLang = targetLang;
    return this;
  }

  /**
   * Get targetLang
   * @return targetLang
   */
  @NotNull 
  @Schema(name = "targetLang", example = "EN", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("targetLang")
  public String getTargetLang() {
    return targetLang;
  }

  public void setTargetLang(String targetLang) {
    this.targetLang = targetLang;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationDTO translationDTO = (TranslationDTO) o;
    return Objects.equals(this.text, translationDTO.text) &&
        Objects.equals(this.sourceLang, translationDTO.sourceLang) &&
        Objects.equals(this.targetLang, translationDTO.targetLang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, sourceLang, targetLang);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationDTO {\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    sourceLang: ").append(toIndentedString(sourceLang)).append("\n");
    sb.append("    targetLang: ").append(toIndentedString(targetLang)).append("\n");
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

