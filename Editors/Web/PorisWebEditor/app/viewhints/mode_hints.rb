class ModeHints < Hobo::ViewHints

  field_names :systems => "Systems to include in:",
  :values => "Values in this mode:"
  children :sub_modes, :values

end