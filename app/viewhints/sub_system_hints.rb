class SubSystemHints < Hobo::ViewHints

  field_names :super_systems => "SuperSystems to include in:",
  :values => "Values in this sub system:"

	children :sub_systems, :labels
end
