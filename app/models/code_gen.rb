class CodeGen < ScopeKind

  def to_code(init,s,doneelems)
    ret="Your Code Here for "+s.to_s
  end

  def to_code_permitted?(init,s,doneelems)
    false
  end

end